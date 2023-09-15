package com.nnk.springboot.controller;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.service.RuleNameService;
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
public class RuleNameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RuleNameService ruleNameService;

    @Test
    public void testHome() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/ruleName/list")
                        .with(SecurityMockMvcRequestPostProcessors.user("username").password("password")))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/list"));
    }

    @Test
    public void testAddRuleForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/ruleName/add")
                        .with(SecurityMockMvcRequestPostProcessors.user("username").password("password")))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"));
    }

    @Test
    public void testValidate() throws Exception {
        RuleName ruleName = new RuleName();
        ruleName.setName("Test");
        ruleName.setDescription("Test");
        ruleName.setJson("Test");
        ruleName.setSqlStr("Test");
        ruleName.setSqlPart("Test");
        ruleName.setTemplate("Test");

        when(ruleNameService.saveRuleName(any())).thenReturn(ruleName);

        mockMvc.perform(MockMvcRequestBuilders.post("/ruleName/validate")
                        .with(SecurityMockMvcRequestPostProcessors.user("username").password("password"))
                .param("name", ruleName.getName())
                .param("description", ruleName.getDescription())
                .param("json", ruleName.getJson())
                .param("sqlStr", ruleName.getSqlStr())
                .param("sqlPart", ruleName.getSqlPart())
                .param("template", ruleName.getTemplate()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));
    }

    @Test
    public void testShowUpdateForm() throws Exception {
        Integer id = 1;
        RuleName ruleName = new RuleName();
        when(ruleNameService.getRuleNameById(id)).thenReturn(Optional.of(ruleName));

        mockMvc.perform(MockMvcRequestBuilders.get("/ruleName/update/" + id)
                        .with(SecurityMockMvcRequestPostProcessors.user("username").password("password")))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/update"));
    }

    @Test
    public void testUpdateRuleName() throws Exception {
        Integer id = 1;
        RuleName ruleName = new RuleName();
        ruleName.setName("Test");
        ruleName.setDescription("Test");
        ruleName.setJson("Test");
        ruleName.setSqlStr("Test");
        ruleName.setSqlPart("Test");
        ruleName.setTemplate("Test");

        when(ruleNameService.updateRuleName(eq(id), any())).thenReturn(ruleName);

        mockMvc.perform(MockMvcRequestBuilders.post("/ruleName/update/" + id)
                        .with(SecurityMockMvcRequestPostProcessors.user("username").password("password"))
                        .param("name", ruleName.getName())
                        .param("description", ruleName.getDescription())
                        .param("json", ruleName.getJson())
                        .param("sqlStr", ruleName.getSqlStr())
                        .param("sqlPart", ruleName.getSqlPart())
                        .param("template", ruleName.getTemplate()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));
    }

    @Test
    public void testDeleteRuleName() throws Exception {
        RuleName ruleName = new RuleName();
        Integer id = 1;
        doReturn(ruleName).when(ruleNameService).deleteRuleNameById(id);

        mockMvc.perform(MockMvcRequestBuilders.get("/ruleName/delete/" + id)
                        .with(SecurityMockMvcRequestPostProcessors.user("username").password("password")))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));
    }
}
