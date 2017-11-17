package com.cooksys.tweetapi.mapper;

import org.mapstruct.Mapper;

import com.cooksys.tweetapi.dto.TweetDTO;
import com.cooksys.tweetapi.entity.Tweet;

@Mapper(componentModel = "spring")
public interface TweetMapper {

    TweetDTO toDto(Tweet tweet);

    Tweet fromDto(TweetDTO dto);

}
