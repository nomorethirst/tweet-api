package com.cooksys.tweetapi.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.cooksys.tweetapi.entity.Credentials;

public class CredentialsDTO {

    @NotNull
    @Valid
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

}
