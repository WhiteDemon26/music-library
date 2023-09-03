package com.example.musiclibrary.controller;

import com.example.musiclibrary.model.User;
import com.example.musiclibrary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UsersController {


    @Autowired
    UserService userService;


    @GetMapping("/show_users")
    public ResponseEntity<List<User>> showUsers() {
        String message = "you asked to see the all Users (see this response's body) !!";
        System.out.println(message);
        return new ResponseEntity<>(userService.findUsers(), HttpStatus.OK);
    }


    @PostMapping("/add_user")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        User submittedUser = userService.addUser(user);
        String message = "You added your user on DB (see this response's body) !!";
        System.out.println(message);
        return new ResponseEntity<>(submittedUser, HttpStatus.OK);
    }
}
