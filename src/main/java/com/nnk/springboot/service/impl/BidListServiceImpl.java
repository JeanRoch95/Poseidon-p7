package com.nnk.springboot.service.impl;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.service.BidListService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BidListServiceImpl implements BidListService {

    private final BidListRepository bidListRepository;

    public BidListServiceImpl(BidListRepository bidListRepository) {
        this.bidListRepository = bidListRepository;
    }

    @Override
    public List<BidList> getAllBid() {
        return bidListRepository.findAll();
    }

    @Override
    public BidList saveBidList(BidList bidList) {
        bidListRepository.save(bidList);
        return bidList;
    }

    @Override
    public Optional<BidList> getBidById(Integer id) {
        Optional<BidList> bidList = bidListRepository.findById(id);
        return bidList;
    }

    @Override
    public BidList updateBidList(Integer id, BidList updatedList) {

        Optional<BidList> bidList = bidListRepository.findById(id);

        if (!bidList.isPresent()) {
            throw new IllegalArgumentException("BidList Not Exist");
        }

        BidList existingBid = bidList.get();
        existingBid.setBidQuantity(updatedList.getBidQuantity());
        existingBid.setType(updatedList.getType());
        existingBid.setAccount(updatedList.getAccount());

        bidListRepository.save(existingBid);
        return existingBid;
    }

    @Override
    public BidList deleteBidById(Integer id) {
        Optional<BidList> bidList = bidListRepository.findById(id);

        if (!bidList.isPresent()) {
            throw new IllegalArgumentException("BidList Not Exist");
        }
        bidListRepository.deleteById(id);
        return bidList.get();
    }
}
