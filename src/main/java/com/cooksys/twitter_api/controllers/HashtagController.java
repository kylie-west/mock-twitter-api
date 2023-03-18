package com.cooksys.twitter_api.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.twitter_api.dtos.HashtagResponseDto;
import com.cooksys.twitter_api.dtos.TweetResponseDto;
import com.cooksys.twitter_api.entities.Hashtag;
import com.cooksys.twitter_api.entities.Tweet;
import com.cooksys.twitter_api.services.HashtagService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tags")
public class HashtagController {

	private final HashtagService hashtagService;

	@GetMapping
	private List<HashtagResponseDto> GetAllHashtags() {
		return hashtagService.getAllTags();
	}

	@GetMapping("/{label}")
	private List<TweetResponseDto> GetTweetsByTag(@PathVariable String label) {
		return hashtagService.getTweetsByTag(label);
	}
}
