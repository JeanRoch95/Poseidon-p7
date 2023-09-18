package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing bids (BidList).
 */
public interface BidListService {

    /**
     * Retrieves all available bids.
     *
     * @return a list of all bids
     */
    List<BidList> getAllBid();

    /**
     * Saves a new bid.
     *
     * @param bidList the bid to save
     * @return the saved bid
     */
    BidList saveBidList(BidList bidList);

    /**
     * Retrieves a bid by its identifier.
     *
     * @param id the identifier of the bid
     * @return an Optional containing the bid if it exists
     */
    Optional<BidList> getBidById(Integer id);

    /**
     * Updates an existing bid.
     *
     * @param id the identifier of the bid to update
     * @param updatedBid the updated bid
     * @return the updated bid
     */
    BidList updateBidList(Integer id, BidList updatedBid);

    /**
     * Deletes a bid by its identifier.
     *
     * @param id the identifier of the bid to delete
     * @return the deleted bid
     */
    BidList deleteBidById(Integer id);

}
