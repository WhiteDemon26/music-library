package com.example.musiclibrary.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;


@ConfigurationProperties(prefix = "user")
@ConstructorBinding
public class ProfileConfiguration {

    private final String initialUsername;
    private User myProfile;

    public ProfileConfiguration(String initialUsername) {
        this.initialUsername = initialUsername;
    }

    public String getInitialUsername() {
        return initialUsername;
    }

    public User getMyProfile() {
        return myProfile;
    }

    public void setMyProfile(User myProfile) {
        this.myProfile = myProfile;
    }
}
