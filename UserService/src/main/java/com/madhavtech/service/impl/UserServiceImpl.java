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
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
//    @CircuitBreaker(name = "ratingHotelBroker", fallbackMethod = "ratingHotelFallback")
    public User getSingleUser(String userId) {
        // Get user from DB using repository
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found on server : " + userId));

        // Fetch ratings for the user from Rating-Service
        Rating[] ratingOfUser = restTemplate.getForObject(
                "http://RATING-SERVICE/ratings/users/" + user.getUserId(), Rating[].class);

        // Convert array to list
        List<Rating> ratings = Arrays.stream(ratingOfUser).toList();

        // For each rating, fetch hotel info from Hotel-Service and set it
        List<Rating> ratingList = ratings.stream().map(rating -> {
            Hotel hotel = hotelService.getHotel(rating.getHotelId());
            rating.setHotels(hotel);
            return rating;
        }).collect(Collectors.toList());

        // Set ratings in user and return
        user.setRatings(ratingList);
        return user;
    }

    @Override
    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }
}
