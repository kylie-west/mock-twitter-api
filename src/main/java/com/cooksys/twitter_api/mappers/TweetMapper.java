package com.cooksys.twitter_api.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.cooksys.twitter_api.dtos.TweetRequestDto;
import com.cooksys.twitter_api.dtos.TweetResponseDto;
import com.cooksys.twitter_api.entities.Tweet;

@Mapper(componentModel = "spring", uses = { UserMapper.class } )
public interface TweetMapper {
	
	TweetResponseDto entityToDto(Tweet tweet);
	
	List<TweetResponseDto> entitiesToDtos(List<Tweet> tweets);
	
	Tweet dtoToEntity(TweetRequestDto tweetRequestDto);
	
}
