package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.BidList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing BidList entities.
 * <p>
 * This interface extends JpaRepository to provide methods for CRUD operations on BidList entities.
 * Custom query methods can also be defined here.
 * </p>
 */
public interface BidListRepository extends JpaRepository<BidList, Integer> {

}
