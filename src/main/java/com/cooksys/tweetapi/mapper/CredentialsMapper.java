package com.cooksys.tweetapi.mapper;

import org.mapstruct.Mapper;

import com.cooksys.tweetapi.dto.CredentialsDTO;
import com.cooksys.tweetapi.entity.Credentials;

@Mapper(componentModel = "spring")
public interface CredentialsMapper {

    CredentialsDTO toDto(Credentials credentials);

    Credentials fromDto(CredentialsDTO dto);

}
