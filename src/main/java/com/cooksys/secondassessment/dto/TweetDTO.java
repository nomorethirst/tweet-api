package com.cooksys.secondassessment.dto;

import java.sql.Timestamp;

import com.cooksys.secondassessment.entity.User;

public class TweetDTO {
	
	private Integer id;
	
	private User author;
	
	private Timestamp posted;
	
	private String content;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
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
