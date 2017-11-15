package com.cooksys.secondassessment.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.cooksys.secondassessment.dto.CredentialsDTO;
import com.cooksys.secondassessment.dto.CredentialsProfileDTO;
import com.cooksys.secondassessment.dto.UserDTO;
import com.cooksys.secondassessment.entity.Credentials;
import com.cooksys.secondassessment.entity.Profile;
import com.cooksys.secondassessment.entity.User;
import com.cooksys.secondassessment.exceptions.AlreadyExistsException;
import com.cooksys.secondassessment.exceptions.InvalidCredentialsException;
import com.cooksys.secondassessment.exceptions.NotExistsException;
import com.cooksys.secondassessment.mapper.CredentialsMapper;
import com.cooksys.secondassessment.mapper.UserMapper;
import com.cooksys.secondassessment.repository.UserRepository;

@Service
public class UserService {
	
	private UserRepository userRepository;

	private UserMapper userMapper;
	
	private CredentialsService credentialsService;
	
	private CredentialsMapper credentialsMapper;
	
	public UserService(UserRepository userRepository, UserMapper userMapper, 
			CredentialsService credentialsService, CredentialsMapper credentialsMapper) {
		super();
		this.userRepository = userRepository;
		this.userMapper = userMapper;
		this.credentialsService = credentialsService;
		this.credentialsMapper = credentialsMapper;
	}
	
	public List<UserDTO> getAllActiveUsers() {
		return userRepository.findByDeleted(false)
				.stream()
				.map(userMapper::toDto)
				.collect(Collectors.toList());
	}
	
	public boolean usernameExists(String username) {
		return userRepository.findByUsernameAndDeleted(username, false) != null;
	}
	
	public boolean usernameAvailable(String username) {
		return userRepository.findByUsername(username) == null;
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
			throw new NotExistsException(String.format("User '%s' does not exist or is deleted.", username));
		}
		return userMapper.toDto(user);
	}
	
	public UserDTO patchUser(CredentialsProfileDTO dto, String username) throws InvalidCredentialsException, NotExistsException {
		Credentials credentials = dto.getCredentials();
		Profile profile = dto.getProfile();
		if (!credentials.getUsername().equals(username)) {
			throw new InvalidCredentialsException(
					String.format("username '%s' does not match credentials (username '%s').", username, credentials.getUsername()));
		}
		if (!credentialsService.isValid(credentials)) {
			throw new InvalidCredentialsException(
					String.format("Invalid credentials {username: %s, password: %s}.", credentials.getUsername(), credentials.getPassword()));
		}
		User user = userRepository.findByUsername(username);
		if (user == null || user.getDeleted()) {
			throw new NotExistsException(String.format("User '%s'does not exist or is deleted.", username));
		}
		
		user.getProfile().merge(profile);
		user = userRepository.save(user);
		
		return userMapper.toDto(user);
	}
	
	public UserDTO deleteUser(CredentialsDTO credentialsDto, String username) throws InvalidCredentialsException, NotExistsException {
		System.out.println(credentialsDto.toString());
		System.out.println(username);
		Credentials credentials = credentialsMapper.fromDto(credentialsDto);
		if (!credentials.getUsername().equals(username)) {
			throw new InvalidCredentialsException(
					String.format("username '%s' does not match credentials (username '%s').", username, credentials.getUsername()));
		}
		if (!credentialsService.isValid(credentials)) {
			throw new InvalidCredentialsException(
					String.format("Invalid credentials {username: %s, password: %s}.", credentials.getUsername(), credentials.getPassword()));
		}
		User user = userRepository.findByUsername(username);
		if (user == null || user.getDeleted()) {
			throw new NotExistsException(String.format("User '%s'does not exist or is already deleted.", username));
		}
		user.setDeleted(true);
		user = userRepository.save(user);
		
		return userMapper.toDto(user);
	}

}
