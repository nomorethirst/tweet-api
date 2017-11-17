package com.cooksys.tweetapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.tweetapi.mapper.CredentialsMapper;
import com.cooksys.tweetapi.mapper.HashtagMapper;
import com.cooksys.tweetapi.mapper.UserMapper;
import com.cooksys.tweetapi.service.CredentialsService;
import com.cooksys.tweetapi.service.HashtagService;
import com.cooksys.tweetapi.service.UserService;

@RestController
@RequestMapping("validate")
public class ValidateController {

    private HashtagService hashtagService;

    private HashtagMapper hashtagMapper;

    private UserService userService;

    private UserMapper userMapper;

    private CredentialsService credentialsService;

    private CredentialsMapper credentialsMapper;


    public ValidateController(HashtagService hashtagService, HashtagMapper hashtagMapper, UserService userService,
                              UserMapper userMapper, CredentialsService credentialsService, CredentialsMapper credentialsMapper) {
        this.hashtagService = hashtagService;
        this.hashtagMapper = hashtagMapper;
        this.userService = userService;
        this.userMapper = userMapper;
        this.credentialsService = credentialsService;
        this.credentialsMapper = credentialsMapper;
    }

    @GetMapping("/tag/exists/{label}")
    public boolean tagExists(@PathVariable String label) {
        return hashtagService.hashtagExists(label);
    }

    @GetMapping("/username/exists/@{username}")
    public boolean usernameExists(@PathVariable String username) {
        return userService.usernameExists(username);
    }

    @GetMapping("/username/available/@{username}")
    public boolean usernameAvailable(@PathVariable String username) {
        return userService.usernameAvailable(username);
    }

}
