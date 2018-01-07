package com.cooksys.tweetapi.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.cooksys.tweetapi.entity.Credentials;
import com.cooksys.tweetapi.entity.Profile;

public class CredentialsProfileDTO {

    @NotNull
    @Valid
    private Credentials credentials;

    @NotNull
    @Valid
    private Profile profile;

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    @Override
    public String toString() {
        return "CredentialsProfileDTO [credentials=" + credentials.toString() + ", profile=" + profile.toString() + "]";
    }

//    public boolean isValid() {
//        return credentials != null && credentials.isValid() &&
//                profile != null && profile.isValid();
//    }


}
