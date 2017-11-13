package com.cooksys.secondassessment.repository;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
	
	private EntityManager entityManager;

	public UserRepository(EntityManager entityManager) {
		super();
		this.entityManager = entityManager;
	}
	
	

}
