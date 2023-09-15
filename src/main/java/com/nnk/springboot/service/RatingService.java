package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.Rating;

import java.util.List;
import java.util.Optional;

public interface RatingService {

    List<Rating> getAllRating();

    Rating saveRating(Rating rating);

    Optional<Rating> getRatingdById(Integer id);

    Rating updateRating(Integer id, Rating updatedRating);

    Rating deleteRatingById(Integer id);
}
