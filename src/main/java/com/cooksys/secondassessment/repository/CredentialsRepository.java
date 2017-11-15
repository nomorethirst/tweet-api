package com.cooksys.secondassessment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.secondassessment.entity.Credentials;

public interface CredentialsRepository extends JpaRepository<Credentials, Integer> {

	Credentials findByUsername(String username);


}
