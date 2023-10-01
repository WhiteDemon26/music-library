package com.example.musiclibrary.controller;

import com.example.musiclibrary.model.User;
import com.example.musiclibrary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UsersController {


    @Autowired
    UserService userService;


    @GetMapping("/show_users")
    public ResponseEntity<List<User>> showUsers() {
        try {
            List<User> listUsers = userService.findUsers();
            return new ResponseEntity<>(listUsers, HttpStatus.OK);
        } catch (Exception e) {
            String message = "An error occurred while asking to see all the Users. \n Exception: " + e.getClass().getName() + ". Message: " + e.getMessage() + ". Cause: " + e.getCause();
            System.out.println(message);
            return null;
        }
    }


    @PostMapping("/add_user")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        try {
            User submittedUser = userService.addUser(user);
            return new ResponseEntity<>(submittedUser, HttpStatus.OK);
        } catch (Exception e) {
            String message = "An error occurred while asking to add a user !! \n Exception: " + e.getClass().getName() + ". Message: " + e.getMessage() + ". Cause: " + e.getCause();
            System.out.println(message);
            return null;
        }
    }

    @PutMapping("/update_my_profile")
    public ResponseEntity<User> updateProfile(@RequestBody User user) {
        try {
            User reSubmittedUser = userService.updateUserProfile(user);
            return new ResponseEntity<>(reSubmittedUser, HttpStatus.OK);
        } catch(Exception e) {
            String message = "An error occurred, your profile couldn't be updated !! \n Exception: " + e.getClass().getName() + ". Message: " + e.getMessage() + ". Cause: " + e.getCause();
            System.out.println(message);
            return null;
        }
    }
}
