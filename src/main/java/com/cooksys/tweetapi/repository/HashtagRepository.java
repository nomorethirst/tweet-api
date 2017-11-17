package com.cooksys.tweetapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.tweetapi.entity.Hashtag;

public interface HashtagRepository extends JpaRepository<Hashtag, Integer> {

    Hashtag findByLabel(String label);

}
