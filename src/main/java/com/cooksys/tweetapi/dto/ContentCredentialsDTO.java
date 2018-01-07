package com.cooksys.tweetapi.dto;

import com.cooksys.tweetapi.entity.Credentials;

public class ContentCredentialsDTO {

    private String content;

    private Credentials credentials;

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
