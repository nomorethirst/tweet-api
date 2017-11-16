package com.cooksys.secondassessment.dto;

import java.sql.Timestamp;

import com.cooksys.secondassessment.entity.User;

public class TweetDTO {
	
	private Integer id;
	
	private UserDTO author;
	
	private Timestamp posted;
	
	private String content;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public UserDTO getAuthor() {
		return author;
	}

	public void setAuthor(UserDTO author) {
		this.author = author;
	}

	public Timestamp getPosted() {
		return posted;
	}

	public void setPosted(Timestamp posted) {
		this.posted = posted;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	

}
