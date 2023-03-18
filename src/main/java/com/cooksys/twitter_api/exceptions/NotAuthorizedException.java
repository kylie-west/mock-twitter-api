package com.cooksys.twitter_api.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class NotAuthorizedException extends RuntimeException {
	
	private static final long serialVersionUID = 8011499701505794149L;
	private String Message;

}
