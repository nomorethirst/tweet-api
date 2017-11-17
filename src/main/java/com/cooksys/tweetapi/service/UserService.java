package com.cooksys.tweetapi.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.cooksys.tweetapi.dto.CredentialsProfileDTO;
import com.cooksys.tweetapi.dto.TweetDTO;
import com.cooksys.tweetapi.dto.UserDTO;
import com.cooksys.tweetapi.entity.Credentials;
import com.cooksys.tweetapi.entity.Profile;
import com.cooksys.tweetapi.entity.User;
import com.cooksys.tweetapi.exceptions.AlreadyExistsException;
import com.cooksys.tweetapi.exceptions.AlreadyFollowingException;
import com.cooksys.tweetapi.exceptions.InvalidCredentialsException;
import com.cooksys.tweetapi.exceptions.NotExistsException;
import com.cooksys.tweetapi.mapper.CredentialsMapper;
import com.cooksys.tweetapi.mapper.TweetMapper;
import com.cooksys.tweetapi.mapper.UserMapper;
import com.cooksys.tweetapi.repository.UserRepository;

@Service
public class UserService {

    private UserRepository userRepository;

    private UserMapper userMapper;

    private CredentialsService credentialsService;

    private CredentialsMapper credentialsMapper;

    private TweetMapper tweetMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper, CredentialsService credentialsService,
                       CredentialsMapper credentialsMapper, TweetMapper tweetMapper) {
        super();
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.credentialsService = credentialsService;
        this.credentialsMapper = credentialsMapper;
        this.tweetMapper = tweetMapper;
    }

    public List<UserDTO> getAllActiveUsers() {
        return userRepository.findByDeleted(false).stream().map(userMapper::toDto).collect(Collectors.toList());
    }

    public boolean usernameExists(String username) {
        return userRepository.findByUsernameAndDeleted(username, false) != null;
    }

    public boolean usernameAvailable(String username) {
        return userRepository.findByUsername(username) == null;
    }

    @Transactional
    public UserDTO createUser(CredentialsProfileDTO dto) throws AlreadyExistsException {
        // System.out.println("userRepo = " + userRepository);
        User user = userRepository.findByUsername(dto.getCredentials().getUsername());
        if (user == null) {
            credentialsService.createCredentials(dto.getCredentials());
            user = userRepository.save(new User(dto.getCredentials().getUsername(), dto.getProfile(),
                    new Timestamp(System.currentTimeMillis())));
        } else if (user.getDeleted()) {
            user.setDeleted(false);
            user.getProfile().merge(dto.getProfile());
        } else { // user with that username exists already!
            throw new AlreadyExistsException(
                    String.format("username '%s' already exists.", dto.getCredentials().getUsername()));
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

    @Transactional
    public UserDTO patchUser(CredentialsProfileDTO dto, String username)
            throws InvalidCredentialsException, NotExistsException {
        Credentials credentials = dto.getCredentials();
        Profile profile = dto.getProfile();
        if (!credentials.getUsername().equals(username)) {
            throw new InvalidCredentialsException(String.format(
                    "username '%s' does not match credentials (username '%s').", username, credentials.getUsername()));
        }
        if (!credentialsService.isValid(credentials)) {
            throw new InvalidCredentialsException(String.format("Invalid credentials {username: %s, password: %s}.",
                    credentials.getUsername(), credentials.getPassword()));
        }
        User user = userRepository.findByUsername(username);
        if (user == null || user.getDeleted()) {
            throw new NotExistsException(String.format("User '%s'does not exist or is deleted.", username));
        }

        user.getProfile().merge(profile);

        return userMapper.toDto(user);
    }

    @Transactional
    public UserDTO deleteUser(Credentials credentials, String username)
            throws InvalidCredentialsException, NotExistsException {
        if (!credentials.getUsername().equals(username)) {
            throw new InvalidCredentialsException(String.format(
                    "username '%s' does not match credentials (username '%s').", username, credentials.getUsername()));
        }
        if (!credentialsService.isValid(credentials)) {
            throw new InvalidCredentialsException(String.format("Invalid credentials {username: %s, password: %s}.",
                    credentials.getUsername(), credentials.getPassword()));
        }
        User user = userRepository.findByUsername(username);
        if (user == null || user.getDeleted()) {
            throw new NotExistsException(String.format("User '%s' does not exist or is already deleted.", username));
        }
        user.setDeleted(true);

        return userMapper.toDto(user);
    }

    @Transactional
    public void followUser(Credentials credentials, String username)
            throws InvalidCredentialsException, NotExistsException, AlreadyFollowingException {
        if (!credentialsService.isValid(credentials)) {
            throw new InvalidCredentialsException(String.format("Invalid credentials {username: %s, password: %s}.",
                    credentials.getUsername(), credentials.getPassword()));
        }
        User following = userRepository.findByUsername(username);
        if (following == null || following.getDeleted()) {
            throw new NotExistsException(String.format("User '%s' does not exist or is deleted.", username));
        }
        User requestor = userRepository.findByUsername(credentials.getUsername());
        if (requestor == null || requestor.getDeleted()) {
            throw new NotExistsException(
                    String.format("User '%s' does not exist or is deleted.", credentials.getUsername()));
        }

        if (following.getFollowers().contains(requestor) || requestor.getFollowing().contains(following))
            throw new AlreadyFollowingException(String.format("User '%s' is already followed.", username));
        following.getFollowers().add(requestor);
        requestor.getFollowing().add(following);
    }

    @Transactional
    public void unFollowUser(Credentials credentials, String username)
            throws InvalidCredentialsException, NotExistsException, AlreadyFollowingException {

        if (!credentialsService.isValid(credentials)) {
            throw new InvalidCredentialsException(String.format("Invalid credentials {username: %s, password: %s}.",
                    credentials.getUsername(), credentials.getPassword()));
        }

        User following = userRepository.findByUsername(username);
        if (following == null || following.getDeleted()) {
            throw new NotExistsException(String.format("User '%s' does not exist or is deleted.", username));
        }
        User requestor = userRepository.findByUsername(credentials.getUsername());
        if (requestor == null || requestor.getDeleted()) {
            throw new NotExistsException(
                    String.format("User '%s' does not exist or is deleted.", credentials.getUsername()));
        }

        if (!following.getFollowers().contains(requestor) || !requestor.getFollowing().contains(following))
            throw new AlreadyFollowingException(String.format("User '%s' is not followed.", username));

        requestor.getFollowing().remove(following);
        following.getFollowers().remove(requestor);

    }

    public List<UserDTO> getFollowers(String username) throws NotExistsException {
        User user = userRepository.findByUsername(username);

        if (user == null || user.getDeleted()) {
            throw new NotExistsException(String.format("User '%s' does not exist or is deleted.", username));
        }
        return user.getFollowers()
                .stream()
                .filter(follower -> usernameExists(follower.getUsername()))
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<UserDTO> getFollowing(String username) throws NotExistsException {
        User user = userRepository.findByUsername(username);

        if (user == null || user.getDeleted()) {
            throw new NotExistsException(String.format("User '%s' does not exist or is deleted.", username));
        }
        return user.getFollowing()
                .stream()
                .filter(following -> usernameExists(following.getUsername()))
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<TweetDTO> getTweetsForUser(String username) throws NotExistsException {
        User user = userRepository.findByUsername(username);

        if (user == null || user.getDeleted()) {
            throw new NotExistsException(String.format("User '%s' does not exist or is deleted.", username));
        }
        return user.getTweets()
                .stream()
                .filter(tweet -> !tweet.getDeleted())
                .map(tweetMapper::toDto)
                .collect(Collectors.toList());

    }

}
