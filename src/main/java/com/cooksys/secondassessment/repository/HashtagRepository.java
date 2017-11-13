package com.cooksys.secondassessment.repository;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

@Repository
public class HashtagRepository {

	private EntityManager entityManager;

	public HashtagRepository(EntityManager entityManager) {
		super();
		this.entityManager = entityManager;
	}

}
