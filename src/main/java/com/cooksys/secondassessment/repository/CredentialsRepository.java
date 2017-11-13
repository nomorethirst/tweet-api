package com.cooksys.secondassessment.repository;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

@Repository
public class CredentialsRepository {

	private EntityManager entityManager;

	public CredentialsRepository(EntityManager entityManager) {
		super();
		this.entityManager = entityManager;
	}


}
