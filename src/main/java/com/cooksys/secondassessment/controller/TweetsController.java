package com.cooksys.secondassessment.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.secondassessment.dto.ContentCredentialsDTO;
import com.cooksys.secondassessment.dto.TweetDTO;
import com.cooksys.secondassessment.exceptions.InvalidCredentialsException;
import com.cooksys.secondassessment.exceptions.InvalidRequestException;
import com.cooksys.secondassessment.exceptions.NotExistsException;
import com.cooksys.secondassessment.service.TweetService;

@RestController
@RequestMapping("tweets")
public class TweetsController {

    private TweetService tweetService;

    public TweetsController(TweetService tweetService) {
	this.tweetService = tweetService;
    }

    @GetMapping
    public List<TweetDTO> getTweets() {
	return tweetService.getAllActiveTweets();
    }

    @PostMapping
    public TweetDTO createSimpleTweet(@RequestBody ContentCredentialsDTO dto, HttpServletResponse response) throws IOException {
	
	try {
	    if (!dto.isValid())
		throw new InvalidRequestException("Invalid Request body");
	    return tweetService.createSimpleTweet(dto);
	} catch (InvalidCredentialsException e) {
	    response.sendError(e.STATUS_CODE, e.responseMessage);
	    return null;
	} catch (NotExistsException e) {
	    response.sendError(e.STATUS_CODE, e.responseMessage);
	    return null;
	} catch (InvalidRequestException e) {
	    response.sendError(e.STATUS_CODE, e.responseMessage);
	    return null;
	}
    }

}
