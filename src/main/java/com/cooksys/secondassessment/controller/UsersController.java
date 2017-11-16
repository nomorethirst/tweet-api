package com.cooksys.secondassessment.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.secondassessment.dto.CredentialsDTO;
import com.cooksys.secondassessment.dto.CredentialsProfileDTO;
import com.cooksys.secondassessment.dto.UserDTO;
import com.cooksys.secondassessment.entity.Credentials;
import com.cooksys.secondassessment.entity.Profile;
import com.cooksys.secondassessment.exceptions.AlreadyExistsException;
import com.cooksys.secondassessment.exceptions.AlreadyFollowingException;
import com.cooksys.secondassessment.exceptions.InvalidCredentialsException;
import com.cooksys.secondassessment.exceptions.InvalidRequestException;
import com.cooksys.secondassessment.exceptions.NotExistsException;
import com.cooksys.secondassessment.service.UserService;

@RestController
@RequestMapping("users")
public class UsersController {

    private UserService userService;

    public UsersController(UserService userService) {
	this.userService = userService;
    }

    @GetMapping
    public List<UserDTO> getUsers() {
	return userService.getAllActiveUsers();
    }

    @PostMapping
    public UserDTO createUser(@RequestBody CredentialsProfileDTO dto, HttpServletResponse response) throws IOException {
	try {
	    if (!dto.isValid())
		throw new InvalidRequestException("Invalid request body.");
	    return userService.createUser(dto);
	} catch (AlreadyExistsException e) {
	    response.sendError(e.STATUS_CODE, e.responseMessage);
	    return null;
	} catch (InvalidRequestException e) {
	    response.sendError(e.STATUS_CODE, e.responseMessage);
	    return null;
	}
    }

    @GetMapping("/@{username}")
    public UserDTO getUser(@PathVariable String username, HttpServletResponse response) throws IOException {
	try {
	    return userService.getUserByUsername(username);
	} catch (NotExistsException e) {
	    response.sendError(e.STATUS_CODE, e.responseMessage);
	    return null;
	}
    }

    @PatchMapping("/@{username}")
    public UserDTO patchUser(@RequestBody CredentialsProfileDTO dto, @PathVariable String username,
	    HttpServletResponse response) throws IOException {
	try {
	    if (!dto.isValid())
		throw new InvalidRequestException("Invalid request body.");
	    return userService.patchUser(dto, username);
	} catch (NotExistsException e) {
	    response.sendError(e.STATUS_CODE, e.responseMessage);
	    return null;
	} catch (InvalidRequestException e) {
	    response.sendError(e.STATUS_CODE, e.responseMessage);
	    return null;
	} catch (InvalidCredentialsException e) {
	    response.sendError(e.STATUS_CODE, e.responseMessage);
	    return null;
	}
    }

    @DeleteMapping(value = "/@{username}")
    public UserDTO deleteUser(@RequestBody CredentialsDTO credentialsDto, @PathVariable String username,
	    HttpServletResponse response) throws IOException {
	try {
	    if (!credentialsDto.isValid())
		throw new InvalidRequestException("Invalid request body.");
	    return userService.deleteUser(credentialsDto.getCredentials(), username);
	} catch (NotExistsException e) {
	    response.sendError(e.STATUS_CODE, e.responseMessage);
	    return null;
	} catch (InvalidRequestException e) {
	    response.sendError(e.STATUS_CODE, e.responseMessage);
	    return null;
	} catch (InvalidCredentialsException e) {
	    response.sendError(e.STATUS_CODE, e.responseMessage);
	    return null;
	}
    }

    @PostMapping("/@{username}/follow")
    public void followUser(@RequestBody CredentialsDTO credentialsDto, @PathVariable String username,
	    HttpServletResponse response) throws IOException {
	try {
	    if (!credentialsDto.isValid())
		throw new InvalidRequestException("Invalid request body.");
	    userService.followUser(credentialsDto.getCredentials(), username);
	} catch (NotExistsException e) {
	    response.sendError(e.STATUS_CODE, e.responseMessage);
	} catch (AlreadyFollowingException e) {
	    response.sendError(e.STATUS_CODE, e.responseMessage);
	} catch (InvalidRequestException e) {
	    response.sendError(e.STATUS_CODE, e.responseMessage);
	} catch (InvalidCredentialsException e) {
	    response.sendError(e.STATUS_CODE, e.responseMessage);
	}

    }

    @PostMapping("/@{username}/unfollow")
    public void unFollowUser(@RequestBody CredentialsDTO credentialsDto, @PathVariable String username,
	    HttpServletResponse response) throws IOException {
	try {
	    if (!credentialsDto.isValid())
		throw new InvalidRequestException("Invalid request body.");
	    userService.unFollowUser(credentialsDto.getCredentials(), username);
	} catch (InvalidCredentialsException e) {
	    response.sendError(e.STATUS_CODE, e.responseMessage);
	} catch (NotExistsException e) {
	    response.sendError(e.STATUS_CODE, e.responseMessage);
	} catch (AlreadyFollowingException e) {
	    response.sendError(e.STATUS_CODE, e.responseMessage);
	} catch (InvalidRequestException e) {
	    response.sendError(e.STATUS_CODE, e.responseMessage);
	}

    }

    @GetMapping("/@{username}/followers")
    public List<UserDTO> getFollowers(@PathVariable String username, HttpServletResponse response) throws IOException {
	try {
	    return userService.getFollowers(username);
	} catch (NotExistsException e) {
	    response.sendError(e.STATUS_CODE, e.responseMessage);
	    return null;
	}
    }

    @GetMapping("/@{username}/following")
    public List<UserDTO> getFollowing(@PathVariable String username, HttpServletResponse response) throws IOException {
	try {
	    return userService.getFollowing(username);
	} catch (NotExistsException e) {
	    response.sendError(e.STATUS_CODE, e.responseMessage);
	    return null;
	}
    }

}
