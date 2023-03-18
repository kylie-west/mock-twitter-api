package com.cooksys.twitter_api.services;

public interface ValidateService {

	boolean validateHashtagExists(String label);

	boolean validateUsernameExists(String username);

	boolean validateUsernameAvailability(String username);

}
