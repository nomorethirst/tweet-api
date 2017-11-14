package com.cooksys.secondassessment.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.secondassessment.dto.CredentialsProfileDTO;
import com.cooksys.secondassessment.dto.UserDTO;
import com.cooksys.secondassessment.exceptions.AlreadyExistsException;
import com.cooksys.secondassessment.exceptions.NotExistsException;
import com.cooksys.secondassessment.service.UserService;

@RestController
@RequestMapping("users")
public class UsersController {
	
	private UserService userService;

	public UsersController(UserService userService) {
		super();
		this.userService = userService;
	}

	@GetMapping
	public List<UserDTO> getUsers(HttpServletResponse response) {
		return userService.getAllUsers();
	}
	
	@PostMapping
	public UserDTO createUser(@RequestBody CredentialsProfileDTO dto, HttpServletResponse response) throws IOException {
		try {
			System.out.println("userService = " + userService);
			return userService.createUser(dto);
		} catch (AlreadyExistsException e) {
			response.sendError(e.STATUS_CODE, e.getMessage());
			return null;
		}
	}
	
	@GetMapping("users/@{username}")
	public UserDTO getUser(@PathVariable String username, HttpServletResponse response) throws IOException {
		try {
			return userService.getUserByUsername(username);
		} catch (NotExistsException e) {
			response.sendError(e.STATUS_CODE, e.getMessage());
			return null;
		}
		
		
	}

}
