package com.cooksys.secondassessment.mapper;

import org.mapstruct.Mapper;

import com.cooksys.secondassessment.dto.HashtagDTO;
import com.cooksys.secondassessment.entity.Hashtag;

@Mapper(componentModel = "spring")
public interface HashtagMapper {
	
	HashtagDTO toDto(Hashtag hashtag);
	
	Hashtag fromDto(HashtagDTO dto);

}
