package com.cooksys.twitter_api.dtos;

import java.sql.Timestamp;

import com.cooksys.twitter_api.entities.Credentials;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class TweetRequestDto {
	
	private String content;
	
	private Credentials credentials;
	
}
