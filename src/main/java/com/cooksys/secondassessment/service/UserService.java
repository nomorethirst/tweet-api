package com.cooksys.secondassessment.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.cooksys.secondassessment.dto.CredentialsProfileDTO;
import com.cooksys.secondassessment.dto.UserDTO;
import com.cooksys.secondassessment.entity.User;
import com.cooksys.secondassessment.exceptions.AlreadyExistsException;
import com.cooksys.secondassessment.exceptions.NotExistsException;
import com.cooksys.secondassessment.mapper.UserMapper;
import com.cooksys.secondassessment.repository.UserRepository;

@Service
public class UserService {
	
	private UserRepository userRepository;

	private UserMapper userMapper;
	
	private CredentialsService credentialsService;
	
	public UserService(UserRepository userRepository, UserMapper userMapper, CredentialsService credentialsService) {
		super();
		this.userRepository = userRepository;
		this.userMapper = userMapper;
		this.credentialsService = credentialsService;
	}
	
	public List<UserDTO> getAllUsers() {
		return userRepository.findAll()
				.stream()
				.map(userMapper::toDto)
				.collect(Collectors.toList());
	}
	
	public Boolean userExists(String username) {
		return userRepository.findByUsername(username) != null;
	}
	
	public UserDTO createUser(CredentialsProfileDTO dto) throws AlreadyExistsException {
//		System.out.println("userRepo = " + userRepository);
		User user = userRepository.findByUsername(dto.getCredentials().getUsername());
		if (user == null) {
			credentialsService.createCredentials(dto.getCredentials());
			user = userRepository.save(new User(dto.getCredentials().getUsername(),
					dto.getProfile(),
					new Timestamp(System.currentTimeMillis()))
					);
		} else if (user.getDeleted()) {
			user.setDeleted(false);
			//TODO: update other properties (Profile etc?)
		} else { // user with that username exists already!
			throw new AlreadyExistsException(String.format("username '%s' already exists.", dto.getCredentials().getUsername()));
		}
		return userMapper.toDto(user);
	}
	
	public UserDTO getUserByUsername(String username) throws NotExistsException {
		User user = userRepository.findByUsername(username);
		
		if (user == null || user.getDeleted()) {
			throw new NotExistsException(String.format("User '%s'does not exist or is deleted.", username));
		}
		return userMapper.toDto(user);
	}
	
	

}