package com.cooksys.tweetapi.mapper;

import org.mapstruct.Mapper;

import com.cooksys.tweetapi.dto.HashtagDTO;
import com.cooksys.tweetapi.entity.Hashtag;

@Mapper(componentModel = "spring")
public interface HashtagMapper {

    HashtagDTO toDto(Hashtag hashtag);

    Hashtag fromDto(HashtagDTO dto);

}
