package com.cooksys.tweetapi.dto;

import java.sql.Timestamp;

import com.cooksys.tweetapi.entity.Profile;

public class UserDTO {

    private String username;

    private Profile profile;

    private Timestamp joined;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Timestamp getJoined() {
        return joined;
    }

    public void setJoined(Timestamp joined) {
        this.joined = joined;
    }

    public boolean isValid() {
        return username != null &&
                profile != null && profile.isValid() &&
                joined != null;
    }


}
