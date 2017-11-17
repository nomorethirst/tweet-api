package com.cooksys.tweetapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.tweetapi.entity.Credentials;

public interface CredentialsRepository extends JpaRepository<Credentials, Integer> {

    Credentials findByUsername(String username);


}
