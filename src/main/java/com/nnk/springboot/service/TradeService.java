package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.Trade;

import java.util.List;
import java.util.Optional;

public interface TradeService {

    List<Trade> getAllTrade();

    Trade saveTrade(Trade trade);

    Optional<Trade> getTradeById(Integer id);

    Trade updateTrade(Integer id, Trade updatedTrade);

    Trade deleteTradeById(Integer id);
}
