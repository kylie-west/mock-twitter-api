package com.cooksys.twitter_api.services;

import java.util.List;

import com.cooksys.twitter_api.dtos.ContextDto;
import com.cooksys.twitter_api.dtos.CredentialsDto;
import com.cooksys.twitter_api.dtos.HashtagResponseDto;
import com.cooksys.twitter_api.dtos.TweetRequestDto;
import com.cooksys.twitter_api.dtos.TweetResponseDto;
import com.cooksys.twitter_api.dtos.UserRequestDto;
import com.cooksys.twitter_api.dtos.UserResponseDto;

public interface TweetService {

	List<TweetResponseDto> getAllTweets();
	
	TweetResponseDto createTweet(TweetRequestDto tweetRequestDto);

	TweetResponseDto getTweet(Long id);
	
	TweetResponseDto deleteTweet(Long id, CredentialsDto credentialsRequestDto);
	
	void likeTweet(Long id, CredentialsDto credentialsDto);
	
	TweetResponseDto createReply(Long id, TweetRequestDto tweetRequestDto);
	
	TweetResponseDto createRepost(Long id, CredentialsDto credentialsRequestDto);
	
	List<HashtagResponseDto> getHashtags(Long id);

	List<UserResponseDto> getLikes(Long id);
	
	ContextDto getContext(Long id);

	List<TweetResponseDto> getReplies(Long id);

	List<TweetResponseDto> getReposts(Long id);

	List<UserResponseDto> getMentions(Long id);

}
