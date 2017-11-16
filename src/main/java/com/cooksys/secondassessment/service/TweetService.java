package com.cooksys.secondassessment.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.cooksys.secondassessment.dto.ContentCredentialsDTO;
import com.cooksys.secondassessment.dto.CredentialsDTO;
import com.cooksys.secondassessment.dto.TweetDTO;
import com.cooksys.secondassessment.dto.UserDTO;
import com.cooksys.secondassessment.entity.Context;
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
    
    public TweetDTO getTweet(Integer id) throws NotExistsException {
	Tweet tweet = tweetRepository.findByIdAndDeleted(id, false);
	if (tweet == null)
	    throw new NotExistsException(String.format("Tweet not found with id %d.", id));
	TweetDTO tweetDto = tweetMapper.toDto(tweet);
	tweetDto.setAuthor(userMapper.toDto(tweet.getAuthor()));
	return tweetDto;
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
    
    @Transactional
    public TweetDTO deleteTweet(CredentialsDTO dto, Integer id) throws InvalidCredentialsException, NotExistsException {
	Credentials credentials = dto.getCredentials();
	if (!credentialsService.isValid(dto.getCredentials())) {
	    throw new InvalidCredentialsException(String.format("Invalid credentials {username: %s, password: %s}.",
		    credentials.getUsername(), credentials.getPassword()));
	}
	Tweet tweet = tweetRepository.findByIdAndDeleted(id, false);
	if (tweet == null)
	    throw new NotExistsException(String.format("Tweet not found with id %d, or already deleted.", id));
	User requestor = userRepository.findByUsernameAndDeleted(credentials.getUsername(), false);
	if (requestor == null)
	    throw new NotExistsException(String.format("User '%s' does not exist or is deleted.", credentials.getUsername()));
	if (!tweet.getAuthor().equals(requestor))
	    throw new InvalidCredentialsException(String.format("User '%s' is not the author of tweet with id '%d'.", credentials.getUsername(), id));
	
	tweet.setDeleted(true);
	
	TweetDTO tweetDto = tweetMapper.toDto(tweet);
	tweetDto.setAuthor(userMapper.toDto(tweet.getAuthor()));
	return tweetDto;
	
    }
    
    @Transactional
    public void likeTweet(CredentialsDTO dto, Integer id) throws InvalidCredentialsException, NotExistsException {
	Credentials credentials = dto.getCredentials();
	if (!credentialsService.isValid(dto.getCredentials())) {
	    throw new InvalidCredentialsException(String.format("Invalid credentials {username: %s, password: %s}.",
		    credentials.getUsername(), credentials.getPassword()));
	}
	Tweet tweet = tweetRepository.findByIdAndDeleted(id, false);
	if (tweet == null)
	    throw new NotExistsException(String.format("Tweet not found with id %d, or already deleted.", id));
	User requestor = userRepository.findByUsernameAndDeleted(credentials.getUsername(), false);
	if (requestor == null)
	    throw new NotExistsException(String.format("User '%s' does not exist or is deleted.", credentials.getUsername()));
	
	tweet.getLikes().add(requestor);
	
    }
    
    @Transactional
    public TweetDTO replyTweet(ContentCredentialsDTO dto, Integer id) throws InvalidCredentialsException, NotExistsException {
	String content = dto.getContent();
	Credentials credentials = dto.getCredentials();
	if (!credentialsService.isValid(dto.getCredentials())) {
	    throw new InvalidCredentialsException(String.format("Invalid credentials {username: %s, password: %s}.",
		    credentials.getUsername(), credentials.getPassword()));
	}
	User requestor = userRepository.findByUsernameAndDeleted(credentials.getUsername(), false);
	if (requestor == null)
	    throw new NotExistsException(String.format("User '%s' does not exist or is deleted.", credentials.getUsername()));

	Tweet inReplyTo = tweetRepository.findByIdAndDeleted(id, false);
	if (inReplyTo == null)
	    throw new NotExistsException(String.format("Tweet not found with id %d, or already deleted.", id));

	Tweet tweet = tweetRepository.save( new Tweet(
		requestor, 
		new Timestamp(System.currentTimeMillis()),
		content,
		inReplyTo)
		);
	parseContent(tweet);
	inReplyTo.getReplies().add(tweet);
	TweetDTO tweetDto = tweetMapper.toDto(tweet);
	tweetDto.setAuthor(userMapper.toDto(tweet.getAuthor()));
	return tweetDto;
    }

    @Transactional
    public TweetDTO repostTweet(CredentialsDTO dto, Integer id) throws InvalidCredentialsException, NotExistsException {
	Credentials credentials = dto.getCredentials();
	if (!credentialsService.isValid(dto.getCredentials())) {
	    throw new InvalidCredentialsException(String.format("Invalid credentials {username: %s, password: %s}.",
		    credentials.getUsername(), credentials.getPassword()));
	}
	User requestor = userRepository.findByUsernameAndDeleted(credentials.getUsername(), false);
	if (requestor == null)
	    throw new NotExistsException(String.format("User '%s' does not exist or is deleted.", credentials.getUsername()));

	Tweet repostOf = tweetRepository.findByIdAndDeleted(id, false);
	if (repostOf == null)
	    throw new NotExistsException(String.format("Tweet not found with id %d, or already deleted.", id));

	Tweet tweet = tweetRepository.save( new Tweet(
		requestor, 
		new Timestamp(System.currentTimeMillis()),
		repostOf)
		);
	repostOf.getReposts().add(tweet);
	TweetDTO tweetDto = tweetMapper.toDto(tweet);
	tweetDto.setAuthor(userMapper.toDto(tweet.getAuthor()));
	return tweetDto;
    }

    public List<Hashtag> getTags(Integer id) throws NotExistsException {
	Tweet tweet = tweetRepository.findByIdAndDeleted(id, false);
	if (tweet == null)
	    throw new NotExistsException(String.format("Tweet not found with id %d.", id));
	return tweet.getTags();
    }
    
    public List<UserDTO> getLikes(Integer id) throws NotExistsException {
	Tweet tweet = tweetRepository.findByIdAndDeleted(id, false);
	if (tweet == null)
	    throw new NotExistsException(String.format("Tweet not found with id %d.", id));
	return tweet.getLikes()
		.stream()
		.filter(user -> userService.usernameExists(user.getUsername()))
		.map(userMapper::toDto)
		.collect(Collectors.toList());
    }
    
    public Context getContext(Integer id) throws NotExistsException {
	Tweet target = tweetRepository.findByIdAndDeleted(id, false);
	if (target == null)
	    throw new NotExistsException(String.format("Tweet not found with id %d.", id));

	List<Tweet> acc = new ArrayList<Tweet>();
	List<TweetDTO> before = getBefore(target, acc)
		.stream()
		.map(tweetMapper::toDto)
		.collect(Collectors.toList());
	List<TweetDTO> after = null;//getAfter(target);
	
	return new Context(tweetMapper.toDto(target), before, after);
    }
    
//    private List<Tweet> getAfter(Tweet target, List<Tweet> after) {
//	if (!target.hasReplies()) {
//	    return after;
//	} else {
//	    for (Tweet reply: target.getReplies()) {
//		List<Tweet> list 
//		after.addAll(getAfter(reply, ))
//	    }
//	}
//	
//    }

    private List<Tweet> getBefore(Tweet target, List<Tweet> before) {
	if (!target.hasInReplyTo()) {
	    return before;
	} else {
	    before.add(target);
	    return getBefore(target.getInReplyTo(), before);
	}
    }

    public List<TweetDTO> getReplies(Integer id) throws NotExistsException {
	Tweet tweet = tweetRepository.findByIdAndDeleted(id, false);
	if (tweet == null)
	    throw new NotExistsException(String.format("Tweet not found with id %d.", id));
	return tweet.getReplies()
		.stream()
		.filter(reply -> !reply.getDeleted() )
		.map(tweetMapper::toDto)
		.collect(Collectors.toList());
    }
    
    public List<TweetDTO> getReposts(Integer id) throws NotExistsException {
	Tweet tweet = tweetRepository.findByIdAndDeleted(id, false);
	if (tweet == null)
	    throw new NotExistsException(String.format("Tweet not found with id %d.", id));
	return tweet.getReposts()
		.stream()
		.filter(repost -> !repost.getDeleted() )
		.map(tweetMapper::toDto)
		.collect(Collectors.toList());
    }
    
    public List<UserDTO> getMentions(Integer id) throws NotExistsException {
	Tweet tweet = tweetRepository.findByIdAndDeleted(id, false);
	if (tweet == null)
	    throw new NotExistsException(String.format("Tweet not found with id %d.", id));
	return tweet.getMentions()
		.stream()
		.filter(user -> userService.usernameExists(user.getUsername()))
		.map(userMapper::toDto)
		.collect(Collectors.toList());
    }
    
}
