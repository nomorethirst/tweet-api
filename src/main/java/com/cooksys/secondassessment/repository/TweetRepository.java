package com.cooksys.secondassessment.repository;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

@Repository
public class TweetRepository {

	private EntityManager entityManager;

	public TweetRepository(EntityManager entityManager) {
		super();
		this.entityManager = entityManager;
	}

}
