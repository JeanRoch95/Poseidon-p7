package com.nnk.springboot.controller;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.service.CurvePointService;
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
public class CurvePointControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CurvePointService curvePointService;

    @Test
    public void testHome() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/curvePoint/list")
                        .with(SecurityMockMvcRequestPostProcessors.user("username").password("password")))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/list"));
    }

    @Test
    public void testAddCurveForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/curvePoint/add")
                        .with(SecurityMockMvcRequestPostProcessors.user("username").password("password")))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"));
    }

    @Test
    public void testValidate() throws Exception {
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setTerm(10.0);
        curvePoint.setValue(10.0);

        when(curvePointService.saveCurvePoint(any())).thenReturn(curvePoint);

        mockMvc.perform(MockMvcRequestBuilders.post("/curvePoint/validate")
                        .with(SecurityMockMvcRequestPostProcessors.user("username").password("password"))
                .param("term", curvePoint.getTerm().toString())
                .param("value", curvePoint.getValue().toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));
    }

    @Test
    public void testShowUpdateForm() throws Exception {
        Integer id = 1;
        CurvePoint curvePoint = new CurvePoint();

        when(curvePointService.getCurvePointById(id)).thenReturn(Optional.of(curvePoint));

        mockMvc.perform(MockMvcRequestBuilders.get("/curvePoint/update/" + id)
                        .with(SecurityMockMvcRequestPostProcessors.user("username").password("password")))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/update"));
    }

    @Test
    public void testUpdateCurve_WithoutErrors() throws Exception {
        Integer id = 1;
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setTerm(10.0);
        curvePoint.setValue(10.0);

        when(curvePointService.updateCurvePoint(eq(id), any())).thenReturn(curvePoint);

        mockMvc.perform(MockMvcRequestBuilders.post("/curvePoint/update/" + id)
                        .with(SecurityMockMvcRequestPostProcessors.user("username").password("password"))
                        .param("term", curvePoint.getTerm().toString())
                        .param("value", curvePoint.getValue().toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));
    }


    @Test
    public void testUpdateCurve_WithErrors() throws Exception {
        Integer id = 1;
        CurvePoint curvePoint = new CurvePoint();
        when(curvePointService.updateCurvePoint(eq(id), any())).thenReturn(curvePoint);

        mockMvc.perform(MockMvcRequestBuilders.post("/curvePoint/update/" + id)
                        .with(SecurityMockMvcRequestPostProcessors.user("username").password("password"))
                        .param("someField", "someValueThatCausesValidationFailure"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/update"))
                .andExpect(model().attributeExists("errors"));
    }

    @Test
    public void testDeleteCurve() throws Exception {
        CurvePoint curvePoint = new CurvePoint();
        Integer id = 1;
        doReturn(curvePoint).when(curvePointService).deleteCurvePointdById(id);

        mockMvc.perform(MockMvcRequestBuilders.get("/curvePoint/delete/" + id)
                        .with(SecurityMockMvcRequestPostProcessors.user("username").password("password")))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));
    }
}
