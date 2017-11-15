package com.cooksys.secondassessment.dto;

public class CredentialsDTO {
	
	private String username;
	
	private String password;
	
	

	public CredentialsDTO() {}

	public CredentialsDTO(String username, String password) {
	    super();
	    this.username = username;
	    this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean usernameIsNull() {
		return this.username == null;
	}

	public boolean passwordIsNull() {
		return this.password == null;
	}

	@Override
	public String toString() {
		return "CredentialsDTO [username=" + username + ", password=" + password + "]";
	}
}
