package com.cooksys.twitter_api.services;

import java.util.List;

import com.cooksys.twitter_api.dtos.CredentialsDto;
import com.cooksys.twitter_api.dtos.ProfileDto;
import com.cooksys.twitter_api.dtos.TweetResponseDto;
import com.cooksys.twitter_api.dtos.UserRequestDto;
import com.cooksys.twitter_api.dtos.UserResponseDto;


public interface UserService {

	List<UserResponseDto> getAllUsers();

	UserResponseDto createUser(UserRequestDto userRequestDto);

	UserResponseDto getOneUser(String username);

	UserResponseDto updateUserProfile(String username, UserRequestDto userRequestDto);

	UserResponseDto deleteUser(String username, CredentialsDto credentialsRequestDto);

	void followUser(String username, CredentialsDto credentialsRequestDto);

	void unfollowUser(String username, CredentialsDto credentialsRequestDto);

	List<TweetResponseDto> getFeed(String username);

	List<TweetResponseDto> getTweets(String username);

	List<TweetResponseDto> getMentions(String username);

	List<UserResponseDto> getFollowers(String username);

	List<UserResponseDto> getFollowing(String username);

}
