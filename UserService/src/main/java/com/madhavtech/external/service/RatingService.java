package com.madhavtech.external.service;

import com.madhavtech.entities.Rating;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@FeignClient(name = "RATING-SERVICE")
public interface RatingService {

    //create
    @PostMapping("/ratings")
    public Rating createRating(Rating rating);

    //getRating
    @PutMapping(name = "/ratings/{ratingId}")
    public Rating updateRating(@PathVariable String ratingId, @RequestBody Rating rating);

    //deleteRating
    @DeleteMapping("/ratings/{ratingId}")
    public void deleteRating(@PathVariable String ratingId);

}
