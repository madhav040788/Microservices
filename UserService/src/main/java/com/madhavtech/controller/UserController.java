package com.madhavtech.controller;

import com.madhavtech.entities.User;
import com.madhavtech.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;

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

    @Autowired
    private UserService userService;

    // createUser
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createNewUser(user));
    }

    //getAll
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsers());
    }
    //getSingle
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable String userId){
        User singleUser = userService.getSingleUser(userId);
        return new ResponseEntity<>(singleUser,HttpStatus.OK);
    }

    //deleteOne
    @DeleteMapping("/userId")
    public ResponseEntity<String> deleteUser(@PathVariable String userid){
        userService.deleteUser(userid);
        return new ResponseEntity<>("User Successfully deleted ..."+userid,HttpStatus.NO_CONTENT);
    }

    //updateUser
}
