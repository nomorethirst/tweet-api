package com.cooksys.secondassessment.entity;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="users")
public class User {

	@Id
	@GeneratedValue
	private Integer id;
	
	@Column(unique=true)
	private String username;
	
	private Profile profile;
	
	private Timestamp joined;
	
	private Boolean deleted = false;
	
	@OneToMany
	private List<User> followers;
	
	@OneToMany
	private List<User> following;
	
	@OneToMany
	private List<Tweet> tweets;
	
	@ManyToMany
	private List<Tweet> mentions;
	
	public User() {}

	public User(String username, Profile profile, Timestamp joined) {
		super();
		this.username = username;
		this.profile = profile;
		this.joined = joined;
	}

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

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
	
	public Integer getId() {
	    return id;
	}

	public void setId(Integer id) {
	    this.id = id;
	}

	public List<User> getFollowers() {
	    return followers;
	}

	public void setFollowers(List<User> followed) {
	    this.followers = followed;
	}

	public List<User> getFollowing() {
	    return following;
	}

	public void setFollowing(List<User> following) {
	    this.following = following;
	}

	public List<Tweet> getTweets() {
	    return tweets;
	}

	public void setTweets(List<Tweet> tweets) {
	    this.tweets = tweets;
	}

	public List<Tweet> getMentions() {
	    return mentions;
	}

	public void setMentions(List<Tweet> mentions) {
	    this.mentions = mentions;
	}
	
	public boolean isValid() {
	    return username != null &&
		    profile != null && profile.isValid() &&
		    joined != null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
	
	
}
