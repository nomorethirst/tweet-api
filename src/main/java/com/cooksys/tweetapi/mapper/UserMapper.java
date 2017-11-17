package com.cooksys.tweetapi.mapper;

import org.mapstruct.Mapper;

import com.cooksys.tweetapi.dto.UserDTO;
import com.cooksys.tweetapi.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toDto(User user);

    User fromDto(UserDTO dto);

}
