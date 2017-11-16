package com.cooksys.secondassessment.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Credentials {
	
	@Id
	private String username;
	
	private String password;
	
	public Credentials() {
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
	
	public boolean isValid() {
	    return username != null && password != null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Credentials other = (Credentials) obj;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Credentials [username=" + username + ", password=" + password + "]";
	}
	
	

}
