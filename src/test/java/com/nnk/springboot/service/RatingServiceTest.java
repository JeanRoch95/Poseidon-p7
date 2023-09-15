package com.nnk.springboot.service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.service.impl.RatingServiceImpl;
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

public class RatingServiceTest {

    @Mock
    private RatingService ratingService;

    @Mock
    private RatingRepository ratingRepository;

    @BeforeEach
    public void setUpBeforeEachTest() {
        ratingService = new RatingServiceImpl(ratingRepository);
    }

    @Test
    public void testGetAllRating() {
        Rating rating1 = new Rating();
        Rating rating2 = new Rating();
        when(ratingRepository.findAll()).thenReturn(Arrays.asList(rating1, rating2));

        List<Rating> result = ratingService.getAllRating();

        assertEquals(2, result.size());
    }

    @Test
    public void testSaveRating() {
        Rating rating = new Rating();
        when(ratingRepository.save(rating)).thenReturn(rating);

        Rating result = ratingService.saveRating(rating);

        assertEquals(rating, result);
    }

    @Test
    public void testGetRatingById() {
        Rating rating = new Rating();
        when(ratingRepository.findById(1)).thenReturn(Optional.of(rating));

        Optional<Rating> result = ratingService.getRatingdById(1);

        assertTrue(result.isPresent());
        assertEquals(rating, result.get());
    }

    @Test
    public void testGetRatingById_NotFound() {
        when(ratingRepository.findById(1)).thenReturn(Optional.empty());

        Optional<Rating> result = ratingService.getRatingdById(1);

        assertFalse(result.isPresent());
    }

    @Test
    public void testUpdateRating() {
        Rating existingRating = new Rating();
        Rating updatedRating = new Rating();
        updatedRating.setFitchRating("A");

        when(ratingRepository.findById(1)).thenReturn(Optional.of(existingRating));
        when(ratingRepository.save(existingRating)).thenReturn(existingRating);

        Rating result = ratingService.updateRating(1, updatedRating);

        assertEquals("A", result.getFitchRating());
    }

    @Test
    public void testUpdateRating_NotFound() {
        Rating updatedRating = new Rating();

        when(ratingRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            ratingService.updateRating(1, updatedRating);
        });
    }

    @Test
    public void testDeleteRatingById() {
        Rating rating = new Rating();
        when(ratingRepository.findById(1)).thenReturn(Optional.of(rating));

        Rating result = ratingService.deleteRatingById(1);

        assertEquals(rating, result);
    }

    @Test
    public void testDeleteRatingById_NotFound() {
        when(ratingRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            ratingService.deleteRatingById(1);
        });
    }
}
