package com.cooksys.twitter_api.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.twitter_api.services.ValidateService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/validate")
public class ValidateController {
	
	private final ValidateService validateService;
	
	@GetMapping("/tag/exists/{label}")
	public boolean validateHashtagExists(@PathVariable String label) {
		return validateService.validateHashtagExists(label);
	}
	
	@GetMapping("/username/exists/@{username}")
	public boolean validateUsernameExists(@PathVariable String username) {
		return validateService.validateUsernameExists(username);
	}
	
	@GetMapping("/username/available/@{username}")
	public boolean validateUsernameAvailability(@PathVariable String username) {
		return validateService.validateUsernameAvailability(username);
	}
	

}
