package com.cooksys.secondassessment.dto;

import com.cooksys.secondassessment.entity.Credentials;
import com.cooksys.secondassessment.entity.Profile;

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
	
	public boolean isValid() {
	    return credentials != null && 
		    credentials.isValid() &&
		    content != null;
	}

}
