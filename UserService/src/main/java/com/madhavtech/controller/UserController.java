package com.madhavtech.controller;

import com.madhavtech.entities.User;
import com.madhavtech.service.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        logger.info("Creating new user: {}", user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createNewUser(user));
    }

    // Fallback method for createUser
    public ResponseEntity<User> createUserFallback(User user, Throwable ex) {
        logger.error("Fallback for createUser invoked. Reason: {}", ex.getMessage());

        User dummyUser = User.builder()
                .userId("00000")
                .name("Fallback User")
                .email("fallback@example.com")
                .about("This is a fallback user because the service is down.")
                .ratings(Collections.emptyList())
                .build();

        return new ResponseEntity<>(dummyUser, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @GetMapping
    @CircuitBreaker(name = "getAllUsersCB",fallbackMethod = "getAllUsersFallback")
    public ResponseEntity<List<User>> getAllUsers(){
        logger.info("Fetching all users");
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // Fallback method for getAllUsers
    public ResponseEntity<List<User>> getAllUsersFallback(Throwable ex){
        logger.error("Fallback for getAllUsers invoked. Reason: {}", ex.getMessage());
        return new ResponseEntity<>(Collections.emptyList(),HttpStatus.SERVICE_UNAVAILABLE);
    }

    int retryCount = 1;
    @GetMapping("/{userId}")
//    @CircuitBreaker(name = "ratingHotelBroker", fallbackMethod = "ratingHotelFallback")
    @Retry(name = "userServiceCB",fallbackMethod = "ratingHotelFallback")
    public ResponseEntity<User> getUserById(@PathVariable String userId){
        logger.info("Fetching user by ID: {}", userId);
        logger.info(" *************  Retry count {}",retryCount);
        retryCount++;
        User user = userService.getSingleUser(userId);
        return ResponseEntity.ok(user);
    }

    //creating fallback method for circuitBreaker
    public ResponseEntity<User> ratingHotelFallback(String userId, Throwable ex) {
        logger.warn("Fallback executed for userId: {} due to error: {}", userId, ex.getMessage());
        User user = User.builder()
                .userId("12345")
                .name("Dummy User")
                .email("dummy@example.com")
                .about("This is a dummy user because the Rating-Service is down.")
                .ratings(new ArrayList<>())
                .build();

        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    @DeleteMapping("/{userId}")
    @CircuitBreaker(name = "deleteUserCB",fallbackMethod = "deleteUserFallback")
    public ResponseEntity<String> deleteUser(@PathVariable String userId){
        logger.info("Deleting user with ID: {}", userId);
        userService.deleteUser(userId);
        return new ResponseEntity<>("User Successfully deleted: " + userId, HttpStatus.NO_CONTENT);
    }
    // Fallback method for deleteUser
    public ResponseEntity<String> deleteUserFallback(String userId, Throwable ex){
        logger.info("fallbackDeletedUserBroker()  fallback is executed for userId: {} due to error: {} ",userId,ex.getMessage());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Fallback is executed with userId while deleting this user ");
    }
}
