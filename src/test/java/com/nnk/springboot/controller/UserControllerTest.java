package com.nnk.springboot.controller;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void testHome() throws Exception {
        List<User> users = Arrays.asList(new User(), new User());
        when(userRepository.findAll()).thenReturn(users);

        mockMvc.perform(MockMvcRequestBuilders.get("/user/list")
                        .with(SecurityMockMvcRequestPostProcessors.user("username").password("password").authorities(new SimpleGrantedAuthority("ADMIN"))))
                .andExpect(status().isOk())
                .andExpect(model().attribute("users", users))
                .andExpect(view().name("user/list"));
    }


    @Test
    public void testAddUserForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/add")
                        .with(SecurityMockMvcRequestPostProcessors.user("username").password("password").authorities(new SimpleGrantedAuthority("ADMIN"))))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"));
    }

    @Test
    public void testValidate() throws Exception {
        User user = new User();
        user.setUsername("Test");
        user.setFullname("Test");
        user.setRole("USER");
        user.setPassword("Testuser1@");
        when(userRepository.save(any())).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/user/validate")
                        .with(SecurityMockMvcRequestPostProcessors.user("username").password("password").authorities(new SimpleGrantedAuthority("ADMIN")))
                        .param("username", user.getUsername())
                        .param("fullname", user.getFullname())
                        .param("role", user.getRole())
                        .param("password", user.getPassword()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));
    }

    @Test
    public void testShowUpdateForm() throws Exception {
        Integer id = 1;
        User user = new User();
        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        mockMvc.perform(MockMvcRequestBuilders.get("/user/update/" + id)
                        .with(SecurityMockMvcRequestPostProcessors.user("username").password("password").authorities(new SimpleGrantedAuthority("ADMIN"))))
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"));
    }

    @Test
    public void testUpdateUser() throws Exception {
        Integer id = 1;
        User user = new User();
        user.setUsername("Test");
        user.setFullname("Test");
        user.setRole("USER");
        user.setPassword("Testuser1@");

        when(userRepository.save(any())).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/user/update/" + id)
                        .with(SecurityMockMvcRequestPostProcessors.user("username").password("password").authorities(new SimpleGrantedAuthority("ADMIN")))
                        .param("username", user.getUsername())
                        .param("fullname", user.getFullname())
                        .param("role", user.getRole())
                        .param("password", user.getPassword()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));
    }

    @Test
    public void testDeleteUser() throws Exception {
        Integer id = 1;
        User user = new User();
        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        mockMvc.perform(MockMvcRequestBuilders.get("/user/delete/" + id)
                        .with(SecurityMockMvcRequestPostProcessors.user("username").password("password").authorities(new SimpleGrantedAuthority("ADMIN"))))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));
    }
}
