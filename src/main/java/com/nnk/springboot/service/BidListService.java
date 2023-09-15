package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;

import java.util.List;
import java.util.Optional;

public interface BidListService {

    List<BidList> getAllBid();

    BidList saveBidList(BidList bidList);

    Optional<BidList> getBidById(Integer id);

    BidList updateBidList(Integer id, BidList updatedBid);

    BidList deleteBidById(Integer id);




}
