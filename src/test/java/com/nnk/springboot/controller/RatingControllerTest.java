package com.nnk.springboot.controller;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;

import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
public class RatingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RatingService ratingService;

    @Test
    public void testHome() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/rating/list")
                        .with(SecurityMockMvcRequestPostProcessors.user("username").password("password")))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/list"));
    }

    @Test
    public void testAddRatingForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/rating/add")
                        .with(SecurityMockMvcRequestPostProcessors.user("username").password("password")))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"));
    }

    @Test
    public void testValidate() throws Exception {
        Rating rating = new Rating();
        rating.setMoodysRating("Test");
        rating.setSandPRating("Test");
        rating.setFitchRating("Test");
        rating.setOrderNumber(10);

        when(ratingService.saveRating(any())).thenReturn(rating);

        mockMvc.perform(MockMvcRequestBuilders.post("/rating/validate")
                        .with(SecurityMockMvcRequestPostProcessors.user("username").password("password"))
                .param("moodysRating", rating.getMoodysRating())
                .param("sandPRating", rating.getSandPRating())
                .param("fitchRating", rating.getFitchRating())
                .param("orderNumber", rating.getOrderNumber().toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));
    }

    @Test
    public void testShowUpdateForm() throws Exception {
        Integer id = 1;
        Rating rating = new Rating();
        when(ratingService.getRatingdById(id)).thenReturn(Optional.of(rating));

        mockMvc.perform(MockMvcRequestBuilders.get("/rating/update/" + id)
                        .with(SecurityMockMvcRequestPostProcessors.user("username").password("password")))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/update"));
    }

    @Test
    public void testUpdateRating() throws Exception {
        Integer id = 1;
        Rating rating = new Rating();
        rating.setMoodysRating("Test");
        rating.setSandPRating("Test");
        rating.setFitchRating("Test");
        rating.setOrderNumber(10);

        when(ratingService.updateRating(eq(id), any())).thenReturn(rating);

        mockMvc.perform(MockMvcRequestBuilders.post("/rating/update/" + id)
                        .with(SecurityMockMvcRequestPostProcessors.user("username").password("password"))
                        .param("moodysRating", rating.getMoodysRating())
                        .param("sandPRating", rating.getSandPRating())
                        .param("fitchRating", rating.getFitchRating())
                        .param("orderNumber", rating.getOrderNumber().toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));
    }

    @Test
    public void testDeleteRating() throws Exception {
        Rating rating = new Rating();
        Integer id = 1;
        doReturn(rating).when(ratingService).deleteRatingById(id);

        mockMvc.perform(MockMvcRequestBuilders.get("/rating/delete/" + id)
                        .with(SecurityMockMvcRequestPostProcessors.user("username").password("password")))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));
    }


}
