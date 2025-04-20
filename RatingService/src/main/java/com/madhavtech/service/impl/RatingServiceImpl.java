package com.madhavtech.service.impl;

import com.madhavtech.entities.Rating;
import com.madhavtech.exceptions.ResourceNotFoundException;
import com.madhavtech.repository.RatingRepository;
import com.madhavtech.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    private RatingRepository ratingRepository;
    @Override
    public Rating giveRating(Rating rating) {
        return ratingRepository.save(rating);
    }

    @Override
    public List<Rating> getAllRatings() {
        return ratingRepository.findAll();
    }

    public Rating getOneRating(String ratingId){
        return ratingRepository.findById(ratingId)
                .orElseThrow(() -> new ResourceNotFoundException("Rating not found ..."));
    }

    @Override
    public List<Rating> getRatingByUserId(String userId) {
        return ratingRepository.findByUserId(userId);
    }

    @Override
    public List<Rating> getRatingByHotelId(String hotelId) {
        return ratingRepository.findByHotelId(hotelId);
    }

    public void deleteRatings(String ratingId){
        ratingRepository.deleteById(ratingId);
    }
}
