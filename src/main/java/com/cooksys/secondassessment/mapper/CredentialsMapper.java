package com.cooksys.secondassessment.mapper;

import org.mapstruct.Mapper;

import com.cooksys.secondassessment.dto.CredentialsDTO;
import com.cooksys.secondassessment.entity.Credentials;

@Mapper(componentModel = "spring")
public interface CredentialsMapper {
	
	CredentialsDTO toDto(Credentials credentials);
	
	Credentials fromDto(CredentialsDTO dto);

}
