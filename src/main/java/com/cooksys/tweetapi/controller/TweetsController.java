package com.cooksys.tweetapi.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.tweetapi.dto.ContentCredentialsDTO;
import com.cooksys.tweetapi.dto.CredentialsDTO;
import com.cooksys.tweetapi.dto.TweetDTO;
import com.cooksys.tweetapi.dto.UserDTO;
import com.cooksys.tweetapi.entity.Context;
import com.cooksys.tweetapi.entity.Hashtag;
import com.cooksys.tweetapi.exceptions.InvalidCredentialsException;
import com.cooksys.tweetapi.exceptions.NotExistsException;
import com.cooksys.tweetapi.service.TweetService;

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

    @GetMapping("/{id}")
    public TweetDTO getTweet(@PathVariable Integer id, HttpServletResponse response) throws IOException {
        try {
            return tweetService.getTweet(id);
        } catch (NotExistsException e) {
            response.sendError(e.STATUS_CODE, e.responseMessage);
            return null;
        }
    }

    @PostMapping
    public TweetDTO createSimpleTweet(@Valid @RequestBody ContentCredentialsDTO dto, HttpServletResponse response) throws IOException {

        try {
            return tweetService.createSimpleTweet(dto);
        } catch (InvalidCredentialsException e) {
            response.sendError(e.STATUS_CODE, e.responseMessage);
            return null;
        } catch (NotExistsException e) {
            response.sendError(e.STATUS_CODE, e.responseMessage);
            return null;
        }
    }

    @DeleteMapping("/{id}")
    public TweetDTO deleteTweet(@Valid @RequestBody CredentialsDTO dto, @PathVariable Integer id,
                                HttpServletResponse response) throws IOException {

        try {
            return tweetService.deleteTweet(dto, id);
        } catch (InvalidCredentialsException e) {
            response.sendError(e.STATUS_CODE, e.responseMessage);
            return null;
        } catch (NotExistsException e) {
            response.sendError(e.STATUS_CODE, e.responseMessage);
            return null;
        }

    }

    @PostMapping("/{id}/like")
    public void likeTweet(@Valid @RequestBody CredentialsDTO dto, @PathVariable Integer id,
                          HttpServletResponse response) throws IOException {
        try {
            tweetService.likeTweet(dto, id);
        } catch (InvalidCredentialsException e) {
            response.sendError(e.STATUS_CODE, e.responseMessage);
        } catch (NotExistsException e) {
            response.sendError(e.STATUS_CODE, e.responseMessage);
        }

    }

    @PostMapping("/{id}/reply")
    public void replyTweet(@Valid @RequestBody ContentCredentialsDTO dto, @PathVariable Integer id,
                           HttpServletResponse response) throws IOException {
        try {
            tweetService.replyTweet(dto, id);
        } catch (InvalidCredentialsException e) {
            response.sendError(e.STATUS_CODE, e.responseMessage);
        } catch (NotExistsException e) {
            response.sendError(e.STATUS_CODE, e.responseMessage);
        }

    }

    @PostMapping("/{id}/repost")
    public void repostTweet(@Valid @RequestBody CredentialsDTO dto, @PathVariable Integer id,
                            HttpServletResponse response) throws IOException {
        try {
            tweetService.repostTweet(dto, id);
        } catch (InvalidCredentialsException e) {
            response.sendError(e.STATUS_CODE, e.responseMessage);
        } catch (NotExistsException e) {
            response.sendError(e.STATUS_CODE, e.responseMessage);
        }

    }

    @GetMapping("/{id}/tags")
    public List<Hashtag> getTagsForTweet(@PathVariable Integer id,
                                         HttpServletResponse response) throws IOException {
        try {
            return tweetService.getTags(id);
        } catch (NotExistsException e) {
            response.sendError(e.STATUS_CODE, e.responseMessage);
            return null;
        }

    }

    @GetMapping("/{id}/likes")
    public List<UserDTO> getLikesForTweet(@PathVariable Integer id,
                                          HttpServletResponse response) throws IOException {
        try {
            return tweetService.getLikes(id);
        } catch (NotExistsException e) {
            response.sendError(e.STATUS_CODE, e.responseMessage);
            return null;
        }

    }

    @GetMapping("/{id}/context")
    public Context getContextForTweet(@PathVariable Integer id,
                                      HttpServletResponse response) throws IOException {
        try {
            return tweetService.getContext(id);
        } catch (NotExistsException e) {
            response.sendError(e.STATUS_CODE, e.responseMessage);
            return null;
        }

    }

    @GetMapping("/{id}/replies")
    public List<TweetDTO> getRepliesForTweet(@PathVariable Integer id,
                                             HttpServletResponse response) throws IOException {
        try {
            return tweetService.getReplies(id);
        } catch (NotExistsException e) {
            response.sendError(e.STATUS_CODE, e.responseMessage);
            return null;
        }

    }

    @GetMapping("/{id}/reposts")
    public List<TweetDTO> getRepostsForTweet(@PathVariable Integer id,
                                             HttpServletResponse response) throws IOException {
        try {
            return tweetService.getReposts(id);
        } catch (NotExistsException e) {
            response.sendError(e.STATUS_CODE, e.responseMessage);
            return null;
        }

    }

    @GetMapping("/{id}/mentions")
    public List<UserDTO> getMentionsForTweet(@PathVariable Integer id,
                                             HttpServletResponse response) throws IOException {
        try {
            return tweetService.getMentions(id);
        } catch (NotExistsException e) {
            response.sendError(e.STATUS_CODE, e.responseMessage);
            return null;
        }

    }

}
