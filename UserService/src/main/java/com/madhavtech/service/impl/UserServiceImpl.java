package com.madhavtech.service.impl;

import com.madhavtech.entities.Hotel;
import com.madhavtech.entities.Rating;
import com.madhavtech.entities.User;
import java.util.UUID;
import java.util.*;
import com.madhavtech.exception.ResourceNotFoundException;
import com.madhavtech.external.service.HotelService;
import com.madhavtech.repository.UserRepository;
import com.madhavtech.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HotelService hotelService;

    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User createNewUser(User user) {
        //generate unique userId
        String randomUserId = UUID.randomUUID().toString();
        user.setUserId(randomUserId);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        List<User> allUsers = userRepository.findAll();
        //implment of rating service call : using RestTemplate
        logger.info("All User from db : {}",allUsers);
        return allUsers;
    }

    //get single user
    @Override
    public User getSingleUser(String userId) {
        //get user from db help of repository
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found on server : " + userId));
        // fetch the rating of the above user from RatingService
        // http://localhost:8083/ratings/users/faa0ebf0-853f-45e2-a5f6-568151cb60a1 => make it dynamic UserId
        Rating[] ratingOfUser = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/"+user.getUserId(), Rating[].class);
        //getting []ofRatings so convert it array to arrayList

        List<Rating> ratings = Arrays.stream(ratingOfUser).toList();

        List<Rating> ratingList = ratings.stream().map(rating -> {
            // api call to hotel service get hotel
            // http://localhost:8082/hotels/4801032c-550a-412f-bea7-959f159c5765
//            ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/"+rating.getHotelId(), Hotel.class);
            Hotel hotel = hotelService.getHotel(rating.getHotelId());
//            logger.info("Response status code : {}",forEntity.getStatusCode());
            //set the hotel to ratings
            rating.setHotels(hotel);
            //return the rating
            return rating;
        }).collect(Collectors.toList());
        user.setRatings(ratingList);
        return user;
    }

    @Override
    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }
}
