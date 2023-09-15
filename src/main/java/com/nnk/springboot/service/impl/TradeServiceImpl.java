package com.nnk.springboot.service.impl;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.service.TradeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TradeServiceImpl implements TradeService {

    private final TradeRepository tradeRepository;

    public TradeServiceImpl(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    @Override
    public List<Trade> getAllTrade() {
        return tradeRepository.findAll();
    }

    @Override
    public Trade saveTrade(Trade trade) {
        tradeRepository.save(trade);
        return trade;
    }

    @Override
    public Optional<Trade> getTradeById(Integer id) {
        Optional<Trade> trade = tradeRepository.findById(id);
        return trade;
    }

    @Override
    public Trade updateTrade(Integer id, Trade updatedTrade) {
        Optional<Trade> trade = tradeRepository.findById(id);

        if (!trade.isPresent()) {
            throw new IllegalArgumentException("RuleName Not Exist");
        }

        Trade existingTrade = trade.get();
        existingTrade.setAccount(updatedTrade.getAccount());
        existingTrade.setType(updatedTrade.getType());
        existingTrade.setBuyQuantity(updatedTrade.getBuyQuantity());


        tradeRepository.save(existingTrade);
        return existingTrade;
    }

    @Override
    public Trade deleteTradeById(Integer id) {
        Optional<Trade> trade = tradeRepository.findById(id);

        if (!trade.isPresent()) {
            throw new IllegalArgumentException("BidList Not Exist");
        }
        tradeRepository.deleteById(id);
        return trade.get();
    }
}
