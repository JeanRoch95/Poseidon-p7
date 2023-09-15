package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.service.impl.BidListServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

public class BidListServiceTest {

    @Mock
    private BidListRepository bidListRepository;

    @Mock
    private BidListService bidListService;

    @BeforeEach
    public void setUpBeforeEachTest() {
        bidListService = new BidListServiceImpl(bidListRepository);
    }

    @Test
    public void testGetAllBid() {
        BidList bid1 = new BidList();
        BidList bid2 = new BidList();
        when(bidListRepository.findAll()).thenReturn(Arrays.asList(bid1, bid2));

        List<BidList> result = bidListService.getAllBid();

        assertEquals(2, result.size());
    }

    @Test
    public void testSaveBidList() {
        BidList bid = new BidList();
        when(bidListRepository.save(bid)).thenReturn(bid);

        BidList result = bidListService.saveBidList(bid);

        assertEquals(bid, result);
    }

    @Test
    public void testGetBidById() {
        BidList bid = new BidList();
        when(bidListRepository.findById(1)).thenReturn(Optional.of(bid));

        Optional<BidList> result = bidListService.getBidById(1);

        assertTrue(result.isPresent());
        assertEquals(bid, result.get());
    }

    @Test
    public void testGetBidById_NotFound() {
        when(bidListRepository.findById(1)).thenReturn(Optional.empty());

        Optional<BidList> result = bidListService.getBidById(1);

        assertFalse(result.isPresent());
    }

    @Test
    public void testUpdateBidList() {
        BidList existingBid = new BidList();
        BidList updatedBid = new BidList();
        updatedBid.setBidQuantity(10.0);

        when(bidListRepository.findById(1)).thenReturn(Optional.of(existingBid));
        when(bidListRepository.save(existingBid)).thenReturn(existingBid);

        BidList result = bidListService.updateBidList(1, updatedBid);

        assertEquals(10, result.getBidQuantity());
    }

    @Test
    public void testUpdateBidList_NotFound() {
        BidList updatedBid = new BidList();

        when(bidListRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            bidListService.updateBidList(1, updatedBid);
        });
    }

    @Test
    public void testDeleteBidById() {
        BidList bid = new BidList();
        when(bidListRepository.findById(1)).thenReturn(Optional.of(bid));

        BidList result = bidListService.deleteBidById(1);

        assertEquals(bid, result);
    }

    @Test
    public void testDeleteBidById_NotFound() {
        when(bidListRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            bidListService.deleteBidById(1);
        });
    }


}
