package com.cooksys.twitter_api.dtos;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class HashtagResponseDto {

	private String label;
	
	private Timestamp firstUsed;
	
	private Timestamp lastUsed;
}
