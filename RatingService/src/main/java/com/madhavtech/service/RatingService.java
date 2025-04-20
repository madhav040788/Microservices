package com.madhavtech.service;

import com.madhavtech.entities.Rating;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.*;

public interface RatingService {
    //giveRating
    Rating giveRating(Rating rating);

    //getAllRating
    List<Rating> getAllRatings();

    //getsingleRating
    Rating getOneRating(String userId);
    //getRatingByUserId
    List<Rating> getRatingByUserId(String userId);

    //getRagingByHotelId
    List<Rating> getRatingByHotelId(String hotelId);
    //deleteRating
    void deleteRatings(String ratingId);
}
