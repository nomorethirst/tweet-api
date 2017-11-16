package com.cooksys.secondassessment.service;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.cooksys.secondassessment.dto.ContentCredentialsDTO;
import com.cooksys.secondassessment.dto.TweetDTO;
import com.cooksys.secondassessment.entity.Credentials;
import com.cooksys.secondassessment.entity.Hashtag;
import com.cooksys.secondassessment.entity.Tweet;
import com.cooksys.secondassessment.entity.User;
import com.cooksys.secondassessment.exceptions.InvalidCredentialsException;
import com.cooksys.secondassessment.exceptions.NotExistsException;
import com.cooksys.secondassessment.mapper.TweetMapper;
import com.cooksys.secondassessment.mapper.UserMapper;
import com.cooksys.secondassessment.repository.TweetRepository;
import com.cooksys.secondassessment.repository.UserRepository;

@Service
public class TweetService {

    private TweetRepository tweetRepository;

    private TweetMapper tweetMapper;

    private CredentialsService credentialsService;

    private UserService userService;
    
    private UserMapper userMapper;
    
    private UserRepository userRepository;
    
    private HashtagService hashtagService;

    public TweetService(TweetRepository tweetRepository, TweetMapper tweetMapper, CredentialsService credentialsService,
	    UserService userService, UserRepository userRepository, UserMapper userMapper, HashtagService hashtagService) {
	this.tweetRepository = tweetRepository;
	this.tweetMapper = tweetMapper;
	this.credentialsService = credentialsService;
	this.userService = userService;
	this.userMapper = userMapper;
	this.userRepository = userRepository;
	this.hashtagService = hashtagService;
    }

    public List<TweetDTO> getAllActiveTweets() {
	return tweetRepository.findByDeletedOrderByPostedDesc(false).stream().map(tweetMapper::toDto)
		.collect(Collectors.toList());
    }
    
    @Transactional
    public void parseContent(Tweet tweet) {
	String content = tweet.getContent();
	List<String> words = Arrays.asList(content.split("\\s+"));
	List<String> labels = words
		.stream()
		.filter( word -> word.startsWith("#") )
		.map( word -> word.substring(1) )
		.collect(Collectors.toList());
	List<String> mentions = words
		.stream()
		.filter( word -> word.startsWith("@") )
		.map( word -> word.substring(1) )
		.collect(Collectors.toList());
	for (String label: labels) {
	    Hashtag tag;
	    if (!hashtagService.hashtagExists(label)) {
		tag = hashtagService.createHashtag(label);
	    } else {
		tag = hashtagService.getHashTag(label);
		tag.setLastUsed(new Timestamp(System.currentTimeMillis()));
	    }
	    tweet.getTags().add(tag);
	}
	for (String mention: mentions) {
	    User user = userRepository.findByUsername(mention);
	    if (user != null) {
		user.getMentions().add(tweet);
		tweet.getMentions().add(user);
	    }
	}
    }

    @Transactional
    public TweetDTO createSimpleTweet(ContentCredentialsDTO dto) throws InvalidCredentialsException, NotExistsException {
	String content = dto.getContent();
	Credentials credentials = dto.getCredentials();
	if (!credentialsService.isValid(dto.getCredentials())) {
	    throw new InvalidCredentialsException(String.format("Invalid credentials {username: %s, password: %s}.",
		    credentials.getUsername(), credentials.getPassword()));
	}
	if (!userService.usernameExists(credentials.getUsername())) {
	    throw new NotExistsException(String.format(
		    "Cannot create tweet for User '%s' - does not exist or is deleted.", credentials.getUsername()));
	}
	
	Tweet tweet = tweetRepository.save( new Tweet(
		userRepository.findByUsername(credentials.getUsername()), 
		new Timestamp(System.currentTimeMillis()),
		content)
		);
	parseContent(tweet);
	TweetDTO tweetDto = tweetMapper.toDto(tweet);
	tweetDto.setAuthor(userMapper.toDto(tweet.getAuthor()));
	return tweetDto;
    }

}
