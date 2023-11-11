package com.example.musiclibrary.service;

import com.example.musiclibrary.model.User;
import com.example.musiclibrary.repository.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;

@Data
@Service
@Transactional
public class UserService {

    @Value("${user.initial-username}")
    private String userName;

    private User myProfile;

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    private void postConstruct() {
        User oldProfile = userRepository.findUserByUserName(userName);
        if(oldProfile != null) {
            LocalDate today = LocalDate.now();
            int updatedAge = (Period.between(oldProfile.getBirthdate(), today).getYears());
            if (updatedAge > oldProfile.getAge()) {
                oldProfile.setAge(updatedAge);
                oldProfile = userRepository.save(oldProfile);
            }
            this.myProfile = oldProfile;
        }
    }


    public User switchProfile(String userName) {
        User profileSwitched = userRepository.findUserByUserName(userName);
        if(profileSwitched != null) {
            this.myProfile = profileSwitched;
        } else {
            System.out.println("Profile not found");
            return null;
        }
        System.out.println("You switched profile !");
        return this.myProfile;
    }


    public User addUser(User user) {
        if(!checkValidityOfPassword(user)) {
            return null;
        }
        if( user.getUserName() == null ) {
            System.out.println("The userName must not be null");
            return null;
        }
        if(userRepository.findUserByUserName(user.getUserName()) != null) {
            System.out.println("This userName is already used!");
            return null;
        }
        user.setRegistration(LocalDateTime.now());
        user = userRepository.save(user);
        System.out.println("The user has been correctly registered.");
        return user;
    }


    public List<User> findUsers() {
        List<User> users = userRepository.findAll();
        String message = "You asked to see all the users (see this response's body) !!";
        System.out.println(message);
        return users;
    }


    public User updateMyProfile(User user) {

        if (user.getFirstName() != null) {
            myProfile.setFirstName(user.getFirstName());
        }
        if (user.getLastName() != null) {
            myProfile.setLastName(user.getLastName());
        }
        if (user.getUserName() != null) {
            myProfile.setUserName(user.getUserName());
        }
        if (user.getPassword() != null & user.getPassword() != user.getOldPassword()) {
            if (checkValidityOfPassword(user)) {
                myProfile.setOldPassword(myProfile.getPassword());
                myProfile.setPassword(user.getPassword());
                System.out.println("You changed your password, good job!)");
            } else {
                System.out.println("You failed to change your password! ");
            }
        }
        if (user.getAddress() != null) {
            myProfile.setAddress(user.getAddress());
        }

        user = userRepository.save(myProfile);

        System.out.println("You successfully changed your profile.");
        return user;
    }


    private boolean checkValidityOfPassword(User user) {

        String password = user.getPassword();
        if(password == null) {
            System.out.println("The password must not be null");
            return false;
        }
        if( userRepository.findByPassword(password) != null ) {
            System.out.println("This password is already used!");
            return false;
        }

        boolean hasSpecialCharacters = password.contains("!") || password.contains("£") || password.contains("$") || password.contains("%");
        boolean containsName = password.contains(user.getFirstName());
        boolean passwordValid = hasSpecialCharacters && !containsName && password.length() >= 8;

        if(containsName) {
            System.out.println("Your password must not contain your name");
        }
        if(!hasSpecialCharacters) {
            System.out.println("Your password must contain at least one special character");
        }
        if(password.length() < 8) {
            System.out.println("Your password must contain at least 8 digits");
        }
        return passwordValid;
    }
}
