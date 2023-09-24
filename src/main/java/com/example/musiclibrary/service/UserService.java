package com.example.musiclibrary.service;

import com.example.musiclibrary.model.User;
import com.example.musiclibrary.repository.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;


@Data
@Service
public class UserService {

    private User myProfile;

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    private void postConstruct() {
        User oldProfile = userRepository.findById(1L).get();
        LocalDate today = LocalDate.now();
        oldProfile.setAge(Period.between(oldProfile.getBirthdate(), today).getYears());
        this.myProfile = oldProfile;
    }

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

        if(user.getFirstName() != null) {
            myProfile.setFirstName(user.getFirstName());
        }
        if(user.getLastName() != null) {
            myProfile.setLastName(user.getLastName());
        }
        if(user.getUserName() != null) {
            myProfile.setUserName(user.getUserName());
        }
        if(user.getPassword() != null & user.getPassword() != user.getOldPassword()) {
            myProfile.setPassword(user.getOldPassword());
        }
        if(user.getAddress() != null) {
            myProfile.setAddress(user.getAddress());
        }

        user = userRepository.save(user);

        System.out.println("You changed your profile! ");
        return user;
    }
}
