package com.nnk.springboot.service.impl;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.service.RatingService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;

    public RatingServiceImpl(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    @Override
    public List<Rating> getAllRating() {
        return ratingRepository.findAll();
    }

    @Override
    public Rating saveRating(Rating rating) {
        ratingRepository.save(rating);
        return rating;
    }

    @Override
    public Optional<Rating> getRatingdById(Integer id) {
        Optional<Rating> rating = ratingRepository.findById(id);
        return rating;
    }

    @Override
    public Rating updateRating(Integer id, Rating updatedRating) {
        Optional<Rating> rating = ratingRepository.findById(id);

        if (!rating.isPresent()) {
            throw new IllegalArgumentException("BidList Not Exist");
        }

        Rating existingRating = rating.get();
        existingRating.setFitchRating(updatedRating.getFitchRating());
        existingRating.setMoodysRating(updatedRating.getMoodysRating());
        existingRating.setOrderNumber(updatedRating.getOrderNumber());
        existingRating.setSandPRating(updatedRating.getSandPRating());

        ratingRepository.save(existingRating);
        return existingRating;
    }

    @Override
    public Rating deleteRatingById(Integer id) {
        Optional<Rating> rating = ratingRepository.findById(id);

        if (!rating.isPresent()) {
            throw new IllegalArgumentException("CurvePoint Not Exist");
        }
        ratingRepository.deleteById(id);
        return rating.get();
    }
}
