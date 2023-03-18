package com.cooksys.twitter_api.services;

import java.util.List;

import com.cooksys.twitter_api.dtos.HashtagResponseDto;
import com.cooksys.twitter_api.dtos.TweetResponseDto;
import com.cooksys.twitter_api.entities.Hashtag;
import com.cooksys.twitter_api.entities.Tweet;

public interface HashtagService {

	List<HashtagResponseDto> getAllTags();

	List<TweetResponseDto> getTweetsByTag(String label);
	
	

}
