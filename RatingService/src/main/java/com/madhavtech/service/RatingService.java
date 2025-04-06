package com.madhavtech.service;

import com.madhavtech.entities.Rating;

import java.util.*;

public interface RatingService {
    //giveRating
    Rating giveRating(Rating rating);

    //getAllRating
    List<Rating> getAllRatings();
    //getRatingByUserId
    List<Rating> getRatingByUserId(String userId);

    //getRagingByHotelId
    List<Rating> getRatingByHotelId(String hotelId);
    //deleteRating
    void deleteRatings(String ratingId);
}
