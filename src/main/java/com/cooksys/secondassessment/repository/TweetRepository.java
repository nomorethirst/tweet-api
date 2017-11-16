package com.cooksys.secondassessment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.secondassessment.entity.Tweet;

public interface TweetRepository extends JpaRepository<Tweet, Integer> {

    List<Tweet> findByAuthor(String username);
    
    Tweet findById(Integer id);
    
    List<Tweet> findByDeletedOrderByPostedDesc(boolean deleted);

}
