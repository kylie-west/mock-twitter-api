package com.cooksys.twitter_api.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@Data
public class Credentials {
	
	@Column(unique = true, nullable = false)
	private String username;
	
	private String password;
}
