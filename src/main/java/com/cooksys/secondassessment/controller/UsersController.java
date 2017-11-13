package com.cooksys.secondassessment.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
public class UsersController {

	public UsersController() {
	}
	
	@GetMapping
	public String getUsers() {
		return "getUsers";
	}

}
