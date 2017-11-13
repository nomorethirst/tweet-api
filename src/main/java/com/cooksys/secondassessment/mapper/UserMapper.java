package com.cooksys.secondassessment.mapper;

import org.mapstruct.Mapper;

import com.cooksys.secondassessment.dto.UserDTO;
import com.cooksys.secondassessment.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
	
	UserDTO toDto(User user);
	
	User fromDto(UserDTO dto);

}
