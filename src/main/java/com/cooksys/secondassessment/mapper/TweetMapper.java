package com.cooksys.secondassessment.mapper;

import org.mapstruct.Mapper;

import com.cooksys.secondassessment.dto.TweetDTO;
import com.cooksys.secondassessment.entity.Tweet;

@Mapper(componentModel = "spring")
public interface TweetMapper {
	
	TweetDTO toDto(Tweet tweet);
	
	Tweet fromDto(TweetDTO dto);

}
