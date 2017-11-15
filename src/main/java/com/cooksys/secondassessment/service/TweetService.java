package com.cooksys.secondassessment.service;

import org.springframework.stereotype.Service;

import com.cooksys.secondassessment.mapper.TweetMapper;
import com.cooksys.secondassessment.repository.TweetRepository;

@Service
public class TweetService {
	
	private TweetRepository tweetRepository;
	
	private TweetMapper tweetMapper;

	public TweetService(TweetRepository tweetRepository, TweetMapper tweetMapper) {
		this.tweetRepository = tweetRepository;
		this.tweetMapper = tweetMapper;
	}
	
	

}
