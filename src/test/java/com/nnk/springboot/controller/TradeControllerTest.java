package com.nnk.springboot.controller;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.service.TradeService;
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
public class TradeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TradeService tradeService;

    @Test
    public void testHome() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/trade/list")
                        .with(SecurityMockMvcRequestPostProcessors.user("username").password("password")))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/list"));
    }

    @Test
    public void testAddTradeForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/trade/add")
                        .with(SecurityMockMvcRequestPostProcessors.user("username").password("password")))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"));
    }

    @Test
    public void testValidate() throws Exception {
        Trade trade = new Trade();
        trade.setAccount("Test");
        trade.setBuyQuantity(10.0);
        trade.setType("Test");

        when(tradeService.saveTrade(any())).thenReturn(trade);

        mockMvc.perform(MockMvcRequestBuilders.post("/trade/validate")
                        .with(SecurityMockMvcRequestPostProcessors.user("username").password("password"))
                        .param("account", trade.getAccount())
                        .param("buyQuantity", trade.getBuyQuantity().toString())
                        .param("type", trade.getType()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));
    }

    @Test
    public void testShowUpdateForm() throws Exception {
        Integer id = 1;
        Trade trade = new Trade();
        when(tradeService.getTradeById(id)).thenReturn(Optional.of(trade));

        mockMvc.perform(MockMvcRequestBuilders.get("/trade/update/" + id)
                        .with(SecurityMockMvcRequestPostProcessors.user("username").password("password")))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/update"));
    }

    @Test
    public void testUpdateTrade() throws Exception {
        Integer id = 1;
        Trade trade = new Trade();
        trade.setAccount("Test");
        trade.setBuyQuantity(10.0);
        trade.setType("Test");

        when(tradeService.updateTrade(eq(id), any())).thenReturn(trade);

        mockMvc.perform(MockMvcRequestBuilders.post("/trade/update/" + id)
                        .with(SecurityMockMvcRequestPostProcessors.user("username").password("password"))
                .param("account", trade.getAccount())
                .param("buyQuantity", trade.getBuyQuantity().toString())
                .param("type", trade.getType()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));
    }

    @Test
    public void testDeleteTrade() throws Exception {
        Trade trade = new Trade();
        Integer id = 1;
        doReturn(trade).when(tradeService).deleteTradeById(id);

        mockMvc.perform(MockMvcRequestBuilders.get("/trade/delete/" + id)
                        .with(SecurityMockMvcRequestPostProcessors.user("username").password("password")))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));
    }


}
