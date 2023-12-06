package com.example.musiclibrary.service;

import com.example.musiclibrary.model.ProfileConfiguration;
import com.example.musiclibrary.model.User;
import com.example.musiclibrary.repository.MusicRepository;
import com.example.musiclibrary.repository.UserRepository;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Data
@Service
@Transactional
public class UserService {


    @Autowired
    private ProfileConfiguration profileConfiguration;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MusicLibraryService musicLibraryService;
    @Autowired
    private MusicRepository musicRepository;


    @PostConstruct
    private void postConstruct() {
        User oldProfile = userRepository.findUserByUserName(profileConfiguration.getInitialUsername());
        if(oldProfile != null) {
            LocalDate today = LocalDate.now();
            int updatedAge = (Period.between(oldProfile.getBirthdate(), today).getYears());
            if (updatedAge > oldProfile.getAge()) {
                oldProfile.setAge(updatedAge);
                oldProfile = userRepository.save(oldProfile);
            }
            profileConfiguration.setMyProfile(oldProfile);
        }
    }


    public User switchProfile(String userName) {
        User profileSwitched = userRepository.findUserByUserName(userName);
        if(profileSwitched != null) {
            profileConfiguration.setMyProfile(profileSwitched);
            musicLibraryService.updateMusicLibrary4NewProfile(userName);
        } else {
            System.out.println("Profile not found");
            return null;
        }
        System.out.println("You switched profile !");
        return profileConfiguration.getMyProfile();
    }


    public User addUser(User user) {

        if(user.getFirstName() == null) {
            System.out.println("Your firstName must not be null");
            return null;
        }
        if( !checkValidityOfPassword(user) ) {
            return null;
        }
        if(user.getUserName() == null) {
            System.out.println("Your userName must not be null");
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


    public User findUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if(!user.isPresent()) {
            System.out.println("the user with the id: " + id + " does not exists !");
            return null;
        }
        String message = "You asked to see the user by the id: " + id + " (see this response's body) !!";
        System.out.println(message);
        return user.get();
    }


    public User updateProfile(User user) {

        User myProfile = profileConfiguration.getMyProfile();

        if (user.getFirstName() != null) {
            myProfile.setFirstName(user.getFirstName());
            System.out.println("You changed your first name, good job!)");
        }
        if (user.getLastName() != null) {
            myProfile.setLastName(user.getLastName());
            System.out.println("You changed your last name, good job!)");
        }

        String newUserName = user.getUserName();
        if(newUserName != null) {
            if(userRepository.findUserByUserName(newUserName) == null) {
                myProfile.setUserName(newUserName);
                System.out.println("You changed your user name, good job!)");
            } else {
                System.out.println("You failed to change your userName because the username is already used you must put one different please! ");
            }
        }

        if ( user.getPassword() != null && !StringUtils.equals(user.getPassword(), myProfile.getOldPassword()) ) {
            if ( checkValidityOfPassword(user) ) {
                myProfile.setOldPassword(myProfile.getPassword());
                myProfile.setPassword(user.getPassword());
                System.out.println("You changed your password, good job!)");
            } else {
                System.out.println("You failed to change your password! ");
            }
        }

        if (user.getAddress() != null) {
            myProfile.setAddress(user.getAddress());
            System.out.println("You changed your address, good job!)");
        }

        profileConfiguration.setMyProfile(myProfile);
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
        if(userRepository.findByPassword(password) != null) {
            System.out.println("This password is already used!");
            return false;
        }

        boolean hasSpecialCharacters = password.contains("!") || password.contains("£") || password.contains("$") || password.contains("%");
        boolean containsName = ( StringUtils.isNotEmpty(user.getFirstName()) ) ? password.contains(user.getFirstName()) : password.contains(profileConfiguration.getMyProfile().getFirstName());
        boolean passwordValid = hasSpecialCharacters && !containsName && password.length() >= 8;

        if(!hasSpecialCharacters) {
            System.out.println("Your password must contain at least one special character");
        }
        if(containsName) {
            System.out.println("Your password must not contain your name");
        }
        if(password.length() < 8) {
            System.out.println("Your password must contain at least 8 digits");
        }
        return passwordValid;
    }
}
