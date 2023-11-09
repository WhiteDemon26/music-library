package com.example.musiclibrary.service;

import com.example.musiclibrary.model.User;
import com.example.musiclibrary.repository.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
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

    private User myProfile;

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    private void postConstruct() {
        if(userRepository.existsById(1L)) {
            User oldProfile = userRepository.findById(1L).get();
            LocalDate today = LocalDate.now();
            int updatedAge = (Period.between(oldProfile.getBirthdate(), today).getYears());
            if (updatedAge > oldProfile.getAge()) {
                oldProfile.setAge(updatedAge);
                oldProfile = userRepository.save(oldProfile);
            }
            this.myProfile = oldProfile;
        }
    }


    public User addUser(User user) {
        if (!checkValidityOfPassword(user)) {
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

        boolean hasSpecialCharacters = password.contains("!") || password.contains("£") || password.contains("$") || password.contains("%");
        boolean containsName = password.contains(user.getFirstName());
        boolean passwordValid = hasSpecialCharacters && !containsName && password.length() >= 8;

        if (containsName) {
            System.out.println("Your password must not contain your name");
        }
        if (!hasSpecialCharacters) {
            System.out.println("Your password must contain at least one special character");
        }
        if (password.length() < 8) {
            System.out.println("Your password must contain at least 8 digits");
        }
        return passwordValid;
    }
}
