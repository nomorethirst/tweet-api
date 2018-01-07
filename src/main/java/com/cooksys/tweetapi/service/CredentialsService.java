package com.cooksys.tweetapi.service;

import org.springframework.stereotype.Service;

import com.cooksys.tweetapi.entity.Credentials;
import com.cooksys.tweetapi.repository.CredentialsRepository;

@Service
public class CredentialsService {

    CredentialsRepository credentialsRepository;

    public CredentialsService(CredentialsRepository credentialsRepository) {
        super();
        this.credentialsRepository = credentialsRepository;
    }

    public void createCredentials(Credentials credentials) {
	//TODO: check username exists
        credentialsRepository.save(credentials);
    }

    public boolean isValid(Credentials credentials) {
        Credentials foundCredentials = credentialsRepository.findByUsername(credentials.getUsername());
        return foundCredentials != null && foundCredentials.getPassword().equals(credentials.getPassword());
    }

}
