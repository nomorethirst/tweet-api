package com.cooksys.tweetapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.tweetapi.entity.Tweet;

public interface TweetRepository extends JpaRepository<Tweet, Integer> {

    List<Tweet> findByAuthor(String username);

    Tweet findById(Integer id);

    Tweet findByIdAndDeleted(Integer id, boolean deleted);

    List<Tweet> findByDeletedOrderByPostedDesc(boolean deleted);

}
