package com.madhavtech.service;

import com.madhavtech.entities.User;
import java.util.*;

public interface UserService {

    //create
     User createNewUser(User user);
    //getallusers
     List<User> getAllUsers();
     //get single user
     User getSingleUser(String userId);

     //deleteUser
    void deleteUser(String userId);

}
