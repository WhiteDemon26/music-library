package com.example.musiclibrary.service;

import com.example.musiclibrary.model.User;
import com.example.musiclibrary.repository.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Data
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public User addUser(User user) {
        user = userRepository.save(user);
        System.out.println("You have completed your profile, congratulations! ");
        return user;
    }


    public List<User> findUsers() {
        List<User> users = userRepository.findAll();
        System.out.println("Those are all the users: " + users);
        return users;
    }


    public User updateUserProfile(User user) {

        user.setPassword(user.getOldPassword());

        System.out.println("You changed your profile, congratulations! ");
        return user;
    }
}
