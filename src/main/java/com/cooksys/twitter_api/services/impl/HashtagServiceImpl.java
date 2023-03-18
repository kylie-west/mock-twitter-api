package com.cooksys.twitter_api.services.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cooksys.twitter_api.dtos.HashtagResponseDto;
import com.cooksys.twitter_api.dtos.TweetResponseDto;
import com.cooksys.twitter_api.entities.Hashtag;
import com.cooksys.twitter_api.entities.Tweet;
import com.cooksys.twitter_api.exceptions.NotFoundException;
import com.cooksys.twitter_api.mappers.HashtagMapper;
import com.cooksys.twitter_api.mappers.TweetMapper;
import com.cooksys.twitter_api.repositories.HashtagRepository;
import com.cooksys.twitter_api.repositories.TweetRepository;
import com.cooksys.twitter_api.services.HashtagService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HashtagServiceImpl implements HashtagService {
	
	private final HashtagRepository hashtagRepository;
	private final HashtagMapper hashtagMapper;
	private final TweetRepository tweetRepository;
	private final TweetMapper tweetMapper;

	@Override
	public List<HashtagResponseDto> getAllTags() {
		return hashtagMapper.entitiesToResponseDtos(hashtagRepository.findAll());
	}

	@Override
	public List<TweetResponseDto> getTweetsByTag(String label) {
		Hashtag tag = hashtagRepository.findByLabelIgnoreCase(label);
		if(tag == null) {
			throw new NotFoundException("Cannot find hashtag with that label.");
		}
		List<Tweet> tweetsByTag = tag.getTweets();
		tweetsByTag.sort(Comparator.comparing(Tweet::getPosted).reversed());
		return tweetMapper.entitiesToDtos(tweetsByTag);
	}

}
