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
import com.cooksys.secondassessment.exceptions.AlreadyExistsException;
import com.cooksys.secondassessment.exceptions.InvalidCredentialsException;
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
		return userService.getAllActiveUsers();
	}
	
	@PostMapping
	public UserDTO createUser(@RequestBody CredentialsProfileDTO dto, HttpServletResponse response) throws IOException {
		try {
			return userService.createUser(dto);
		} catch (AlreadyExistsException e) {
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
			return userService.patchUser(dto, username);
		} catch (InvalidCredentialsException e) {
			response.sendError(e.STATUS_CODE, e.responseMessage);
			return null;
		} catch (NotExistsException e) {
			response.sendError(e.STATUS_CODE, e.responseMessage);
			return null;
		}
	}
	
	@DeleteMapping(value="/@{username}")
	public UserDTO deleteUser(@RequestBody CredentialsDTO credentialsDto, @PathVariable String username, 
			HttpServletResponse response) throws IOException {
		System.out.println(credentialsDto.toString());
		System.out.println(username);
		try {
			if (credentialsDto.usernameIsNull() || credentialsDto.passwordIsNull())
				throw new InvalidCredentialsException("Invalid credentials - username or password is null.");
			return userService.deleteUser(credentialsDto, username);
		} catch (InvalidCredentialsException e) {
			response.sendError(e.STATUS_CODE, e.responseMessage);
			return null;
		} catch (NotExistsException e) {
			response.sendError(e.STATUS_CODE, e.responseMessage);
			return null;
		}
	}
	

}
