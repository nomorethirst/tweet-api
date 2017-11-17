package com.cooksys.tweetapi.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.tweetapi.dto.HashtagDTO;
import com.cooksys.tweetapi.dto.TweetDTO;
import com.cooksys.tweetapi.exceptions.NotExistsException;
import com.cooksys.tweetapi.service.HashtagService;

@RestController
@RequestMapping("tags")
public class HashtagController {

    private HashtagService hashtagService;

    public HashtagController(HashtagService hashtagService) {
        this.hashtagService = hashtagService;
    }

    @GetMapping
    public List<HashtagDTO> getHashtags() {
        return hashtagService.getAllHashtags();
    }

    @GetMapping("/{label}")
    public List<TweetDTO> getHashtag(@PathVariable String label, HttpServletResponse response) throws IOException {
        try {
            if (!hashtagService.hashtagExists(label))
                throw new NotExistsException(String.format("Hashtag with label '%s' does not exist.", label));
            //TODO: refactor Hashtag with List<Tweet> tweets @OneToMany etc.
            return null;
        } catch (NotExistsException e) {
            response.sendError(e.STATUS_CODE, e.responseMessage);
            return null;
        }
    }

}
