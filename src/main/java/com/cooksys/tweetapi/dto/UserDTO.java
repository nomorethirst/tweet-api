package com.cooksys.tweetapi.dto;

import java.sql.Timestamp;

import javax.validation.constraints.NotNull;

import com.cooksys.tweetapi.entity.Profile;

public class UserDTO {

    @NotNull
    private String username;

    @NotNull
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

}
