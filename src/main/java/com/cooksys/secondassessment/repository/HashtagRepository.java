package com.cooksys.secondassessment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.secondassessment.entity.Hashtag;

public interface HashtagRepository extends JpaRepository<Hashtag, Integer> {

	Hashtag findByLabel(String label);

}
