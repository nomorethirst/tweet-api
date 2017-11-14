package com.cooksys.secondassessment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.secondassessment.entity.Tweet;

public interface TweetRepository extends JpaRepository<Tweet, Integer> {


}
