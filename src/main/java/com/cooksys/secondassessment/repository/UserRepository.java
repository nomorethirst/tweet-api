package com.cooksys.secondassessment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.secondassessment.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	
	User findByUsername(String username);

}
