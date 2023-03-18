package com.cooksys.twitter_api.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.cooksys.twitter_api.dtos.CredentialsDto;
import com.cooksys.twitter_api.dtos.ProfileDto;
import com.cooksys.twitter_api.dtos.UserRequestDto;
import com.cooksys.twitter_api.dtos.UserResponseDto;
import com.cooksys.twitter_api.entities.User;

@Mapper(componentModel = "spring", uses= {CredentialsMapper.class, ProfileMapper.class})
public interface UserMapper {

	@Mapping(target="username", source="credentials.username")
	UserResponseDto entityToDto(User user);
	
	List<UserResponseDto> entitiesToDtos(List<User> users);
	
	
	User requestDtoToEntity(UserRequestDto userRequestDto);
	
}
