package com.cooksys.tweetapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.tweetapi.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);

    List<User> findByDeleted(boolean deleted);

    User findByUsernameAndDeleted(String username, boolean deleted);

}
