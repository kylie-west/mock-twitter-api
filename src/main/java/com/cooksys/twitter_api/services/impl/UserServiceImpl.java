package com.cooksys.twitter_api.services.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cooksys.twitter_api.dtos.CredentialsDto;
import com.cooksys.twitter_api.dtos.TweetResponseDto;
import com.cooksys.twitter_api.dtos.UserRequestDto;
import com.cooksys.twitter_api.dtos.UserResponseDto;
import com.cooksys.twitter_api.entities.Credentials;
import com.cooksys.twitter_api.entities.Profile;
import com.cooksys.twitter_api.entities.Tweet;
import com.cooksys.twitter_api.entities.User;
import com.cooksys.twitter_api.exceptions.BadRequestException;
import com.cooksys.twitter_api.exceptions.NotAuthorizedException;
import com.cooksys.twitter_api.exceptions.NotFoundException;
import com.cooksys.twitter_api.mappers.CredentialsMapper;
import com.cooksys.twitter_api.mappers.ProfileMapper;
import com.cooksys.twitter_api.mappers.TweetMapper;
import com.cooksys.twitter_api.mappers.UserMapper;
import com.cooksys.twitter_api.repositories.TweetRepository;
import com.cooksys.twitter_api.repositories.UserRepository;
import com.cooksys.twitter_api.services.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final UserMapper userMapper;
	private final CredentialsMapper credentialsMapper;
	private final ProfileMapper profileMapper;
	private final TweetMapper tweetMapper;
	private final TweetRepository tweetRepository;

	// Helper method to verify and return the user from the database
	private User getUserFromDatabase(String username) {
		User user = userRepository.findByCredentials_Username(username);
		if (user == null) {
			throw new NotFoundException("No user with username: " + username);
		}
		if (user.isDeleted()) {
			throw new NotFoundException("User with username: " + username + " has been deleted.");
		}
		return user;
	}

	private void validateUserRequest(UserRequestDto userRequestDto) {
		if (userRequestDto.getCredentials() == null) {
			throw new BadRequestException("Must provide your credentials.");
		}
		if (userRequestDto.getProfile() == null) {
			throw new BadRequestException("Must provide your profile.");
		}

		if (userRequestDto.getCredentials().getUsername() == null) {
			throw new BadRequestException("Must provide your username.");
		}
		if (userRequestDto.getCredentials().getPassword() == null
				|| userRequestDto.getCredentials().getPassword().length() == 0) {
			throw new BadRequestException("Must provide your password.");
		}

	}

	private void validateCredentials(CredentialsDto credentialsRequestDto) {
		if (credentialsRequestDto.getUsername() == null) {
			throw new BadRequestException("Must provide your username.");
		}
		if (credentialsRequestDto.getPassword() == null) {
			throw new BadRequestException("Must provide your password.");
		}
	}

	@Override
	public List<UserResponseDto> getAllUsers() {
		return userMapper.entitiesToDtos(userRepository.findAllByDeletedFalse());
	}

	@Override
	public UserResponseDto createUser(UserRequestDto userRequestDto) {
		validateUserRequest(userRequestDto);
		validateCredentials(userRequestDto.getCredentials());
		if (userRequestDto.getProfile().getEmail() == null) {
			throw new BadRequestException("Must provide your email to create a new user.");
		}

		// Check if username already exists in database and not deleted
		User user = userRepository.findByCredentials_Username(userRequestDto.getCredentials().getUsername());
		if (user != null && !user.isDeleted()) {
			throw new BadRequestException("Username is not available.");
		}

		// If user has previously been deleted, reactive them rather than create a new
		// user
		if (user != null && user.isDeleted()) {
			user.setDeleted(false);

			// Update the reativated user profile with the profile values in the request
			if (userRequestDto.getProfile().getEmail() != null) {
				user.getProfile().setEmail(userRequestDto.getProfile().getEmail());
			}
			if (userRequestDto.getProfile().getFirstName() != null) {
				user.getProfile().setFirstName(userRequestDto.getProfile().getFirstName());
			}
			if (userRequestDto.getProfile().getLastName() != null) {
				user.getProfile().setLastName(userRequestDto.getProfile().getLastName());
			}
			if (userRequestDto.getProfile().getPhone() != null) {
				user.getProfile().setPhone(userRequestDto.getProfile().getPhone());
			}

			return userMapper.entityToDto(userRepository.saveAndFlush(user));
		}

		User userToSave = userMapper.requestDtoToEntity(userRequestDto);
		userToSave.setDeleted(false);

		return userMapper.entityToDto(userRepository.saveAndFlush(userToSave));
	}

	@Override
	public UserResponseDto getOneUser(String username) {
		return userMapper.entityToDto(getUserFromDatabase(username));
	}

	@Override
	public UserResponseDto updateUserProfile(String username, UserRequestDto userRequestDto) {
		validateUserRequest(userRequestDto);
		User userToUpdate = getUserFromDatabase(username);
		Profile profile = profileMapper.requestDtoToEntity(userRequestDto.getProfile());

		if (!userToUpdate.getCredentials().getPassword().equals(userRequestDto.getCredentials().getPassword())) {
			throw new NotAuthorizedException("Password is incorrect");
		}

		/*
		 * If the user does not provide a field in profile to update, these will set the
		 * new value to the old one
		 */
		if (userRequestDto.getProfile().getEmail() == null) {
			profile.setEmail(userToUpdate.getProfile().getEmail());
		}
		if (userRequestDto.getProfile().getFirstName() == null) {
			profile.setFirstName(userToUpdate.getProfile().getFirstName());
		}
		if (userRequestDto.getProfile().getLastName() == null) {
			profile.setLastName(userToUpdate.getProfile().getLastName());
		}
		if (userRequestDto.getProfile().getPhone() == null) {
			profile.setPhone(userToUpdate.getProfile().getPhone());
		}
		userToUpdate.setProfile(profile);

		return userMapper.entityToDto(userRepository.saveAndFlush(userToUpdate));
	}

	@Override
	public UserResponseDto deleteUser(String username, CredentialsDto credentialsRequestDto) {
		User userToDelete = getUserFromDatabase(username);
		Credentials credentials = credentialsMapper.requestDtoToEntity(credentialsRequestDto);

		if (!userToDelete.getCredentials().getPassword().equals(credentials.getPassword())) {
			throw new NotAuthorizedException("Password is incorrect.");
		}
		userToDelete.setDeleted(true);
		return userMapper.entityToDto(userRepository.saveAndFlush(userToDelete));
	}

	@Override
	public void followUser(String username, CredentialsDto credentialsRequestDto) {
		validateCredentials(credentialsRequestDto);
		User userToFollow = getUserFromDatabase(username);
		Credentials credentials = credentialsMapper.requestDtoToEntity(credentialsRequestDto);

		User currentUser = getUserFromDatabase(credentials.getUsername());

		if (!currentUser.getCredentials().getPassword().equals(credentials.getPassword())) {
			throw new NotAuthorizedException("Password is incorrect.");
		}

		// Ensures there is not already a following relationship between the 2 users
		for (User u : currentUser.getFollowing()) {
			if (u.getCredentials().getUsername().equals(userToFollow.getCredentials().getUsername())) {
				throw new BadRequestException("You already follow this user.");
			}
		}
		userToFollow.getFollowers().add(currentUser);

		userMapper.entityToDto(userRepository.saveAndFlush(currentUser));
		userMapper.entityToDto(userRepository.saveAndFlush(userToFollow));

	}

	@Override
	public void unfollowUser(String username, CredentialsDto credentialsRequestDto) {
		validateCredentials(credentialsRequestDto);
		User userToUnfollow = getUserFromDatabase(username);
		Credentials credentials = credentialsMapper.requestDtoToEntity(credentialsRequestDto);

		User currentUser = getUserFromDatabase(credentials.getUsername());

		if (!currentUser.getCredentials().getPassword().equals(credentials.getPassword())) {
			throw new NotAuthorizedException("Password is incorrect.");
		}

		if (!userToUnfollow.getFollowers().contains(currentUser)) {
			throw new BadRequestException("You do not follow this user.");
		}

		userToUnfollow.getFollowers().remove(currentUser);

		userMapper.entityToDto(userRepository.saveAndFlush(currentUser));
		userMapper.entityToDto(userRepository.saveAndFlush(userToUnfollow));
	}

	// Helper for getting list of tweets from list of users
	private List<Tweet> getTweetsFromUsers(List<User> users) {
		List<Tweet> tweets = new ArrayList<>();
		for (User user : users) {
			tweets.addAll(user.getTweets());
		}
		return tweets;
	}

	@Override
	public List<TweetResponseDto> getFeed(String username) {
		User user = getUserFromDatabase(username);
		System.out.println(user.getCredentials().getUsername());
		List<Tweet> tweets = user.getTweets();

		tweets.addAll(getTweetsFromUsers(user.getFollowing()));
		tweets.sort(Comparator.comparing(Tweet::getPosted).reversed());
		return tweetMapper.entitiesToDtos(tweets);
	}

	@Override
	public List<TweetResponseDto> getTweets(String username) {
		List<Tweet> tweets = getUserFromDatabase(username).getTweets();
		tweets.sort(Comparator.comparing(Tweet::getPosted).reversed());
		return tweetMapper.entitiesToDtos(tweets);
	}

	@Override
	public List<TweetResponseDto> getMentions(String username) {
		return tweetMapper.entitiesToDtos(getUserFromDatabase(username).getTweetMentions());
	}

	@Override
	public List<UserResponseDto> getFollowers(String username) {
		return userMapper.entitiesToDtos(getUserFromDatabase(username).getFollowers());
	}

	@Override
	public List<UserResponseDto> getFollowing(String username) {
		return userMapper.entitiesToDtos(getUserFromDatabase(username).getFollowing());
	}

}
