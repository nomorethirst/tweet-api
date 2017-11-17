package com.cooksys.tweetapi.dto;

import com.cooksys.tweetapi.entity.Credentials;

public class CredentialsDTO {

    private Credentials credentials;

    public CredentialsDTO() {
    }

    public CredentialsDTO(Credentials credentials) {
        this.credentials = credentials;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public boolean isValid() {
        return credentials != null && credentials.isValid();
    }

}
