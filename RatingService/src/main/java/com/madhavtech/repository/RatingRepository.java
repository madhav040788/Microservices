package com.madhavtech.repository;

import com.madhavtech.entities.Rating;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.*;

public interface RatingRepository extends MongoRepository<Rating,String> {

    //custom finder methods
    List<Rating> findByUserId(String userId);
    List<Rating> findByHotelId(String hotelId);
}
