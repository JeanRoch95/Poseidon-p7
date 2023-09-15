package com.nnk.springboot.service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.service.impl.TradeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

public class TradeServiceTest {

    @Mock
    private TradeService tradeService;

    @Mock
    private TradeRepository tradeRepository;

    @BeforeEach
    public void setUpBeforeEachTest() {
        tradeService = new TradeServiceImpl(tradeRepository);
    }

    @Test
    public void testGetAllTrade() {
        Trade trade1 = new Trade();
        Trade trade2 = new Trade();
        when(tradeRepository.findAll()).thenReturn(Arrays.asList(trade1, trade2));

        List<Trade> result = tradeService.getAllTrade();

        assertEquals(2, result.size());
    }

    @Test
    public void testSaveTrade() {
        Trade trade = new Trade();
        when(tradeRepository.save(trade)).thenReturn(trade);

        Trade result = tradeService.saveTrade(trade);

        assertEquals(trade, result);
    }

    @Test
    public void testGetTradeById() {
        Trade trade = new Trade();
        when(tradeRepository.findById(1)).thenReturn(Optional.of(trade));

        Optional<Trade> result = tradeService.getTradeById(1);

        assertTrue(result.isPresent());
        assertEquals(trade, result.get());
    }

    @Test
    public void testGetTradeById_NotFound() {
        when(tradeRepository.findById(1)).thenReturn(Optional.empty());

        Optional<Trade> result = tradeService.getTradeById(1);

        assertFalse(result.isPresent());
    }

    @Test
    public void testUpdateTrade() {
        Trade existingTrade = new Trade();
        Trade updatedTrade = new Trade();
        updatedTrade.setAccount("New Account");

        when(tradeRepository.findById(1)).thenReturn(Optional.of(existingTrade));
        when(tradeRepository.save(existingTrade)).thenReturn(existingTrade);

        Trade result = tradeService.updateTrade(1, updatedTrade);

        assertEquals("New Account", result.getAccount());
    }

    @Test
    public void testUpdateTrade_NotFound() {
        Trade updatedTrade = new Trade();

        when(tradeRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            tradeService.updateTrade(1, updatedTrade);
        });
    }

    @Test
    public void testDeleteTradeById() {
        Trade trade = new Trade();
        when(tradeRepository.findById(1)).thenReturn(Optional.of(trade));

        Trade result = tradeService.deleteTradeById(1);

        assertEquals(trade, result);
    }

    @Test
    public void testDeleteTradeById_NotFound() {
        when(tradeRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            tradeService.deleteTradeById(1);
        });
    }
}
