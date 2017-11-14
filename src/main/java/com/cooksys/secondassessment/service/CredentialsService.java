package com.cooksys.secondassessment.service;

import org.springframework.stereotype.Service;

import com.cooksys.secondassessment.entity.Credentials;
import com.cooksys.secondassessment.repository.CredentialsRepository;

@Service
public class CredentialsService {
	
	CredentialsRepository credentialsRepository;

	public CredentialsService(CredentialsRepository credentialsRepository) {
		super();
		this.credentialsRepository = credentialsRepository;
	}
	
	public void createCredentials(Credentials credentials) {
		credentialsRepository.save(credentials);
	}
	

}