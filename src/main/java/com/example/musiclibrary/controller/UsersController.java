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
        return new ResponseEntity<>(userService.findUsers(), HttpStatus.OK);
    }


    @PostMapping("/add_user")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        User submittedUser = userService.addUser(user);
        return new ResponseEntity<>(submittedUser, HttpStatus.OK);
    }

    @PutMapping("/update_my_profile")
    public ResponseEntity<User> updateProfile(@RequestBody User user) {
        User reSubmittedUser = userService.updateUserProfile(user);
        return new ResponseEntity<>(reSubmittedUser, HttpStatus.OK);
    }
}
