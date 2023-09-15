package com.nnk.springboot.controller;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.service.BidListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;

import java.util.Optional;


@SpringBootTest
@AutoConfigureMockMvc
public class BidListControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BidListService bidListService;

    @Test
    public void testHome() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/bidList/list")
                .with(SecurityMockMvcRequestPostProcessors.user("username").password("password")))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/list"));
    }

    @Test
    public void testAddBidForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/bidList/add")
                        .with(SecurityMockMvcRequestPostProcessors.user("username").password("password")))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"));
    }

    @Test
    public void testValidate() throws Exception {
        BidList bid = new BidList();
        bid.setBidQuantity(10.0);
        bid.setAccount("Test");
        bid.setType("Test");
        when(bidListService.saveBidList(any())).thenReturn(bid);

        mockMvc.perform(MockMvcRequestBuilders.post("/bidList/validate")
                        .with(SecurityMockMvcRequestPostProcessors.user("username").password("password"))
                        .param("bidQuantity", bid.getBidQuantity().toString())
                        .param("account", bid.getAccount())
                        .param("type", bid.getType()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));

    }

    @Test
    public void testShowUpdateForm() throws Exception {
        Integer id = 1;
        BidList bid = new BidList();
        when(bidListService.getBidById(id)).thenReturn(Optional.of(bid));

        mockMvc.perform(MockMvcRequestBuilders.get("/bidList/update/" + id)
                        .with(SecurityMockMvcRequestPostProcessors.user("username").password("password")))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/update"));
    }

    @Test
    public void testUpdateBid() throws Exception {
        Integer id = 1;
        BidList bid = new BidList();
        bid.setBidQuantity(10.0);
        bid.setAccount("Test");
        bid.setType("Test");

        when(bidListService.updateBidList(eq(id), any())).thenReturn(bid);

        mockMvc.perform(MockMvcRequestBuilders.post("/bidList/update/" + id)
                        .with(SecurityMockMvcRequestPostProcessors.user("username").password("password"))
                        .param("bidQuantity", bid.getBidQuantity().toString())
                        .param("account", bid.getAccount())
                        .param("type", bid.getType()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));
    }

    @Test
    public void testDeleteBid() throws Exception {
        BidList bidList = new BidList();
        Integer id = 1;
        doReturn(bidList).when(bidListService).deleteBidById(id);

        mockMvc.perform(MockMvcRequestBuilders.get("/bidList/delete/" + id)
                        .with(SecurityMockMvcRequestPostProcessors.user("username").password("password")))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));
    }

}
