package com.cooksys.twitter_api.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cooksys.twitter_api.dtos.CredentialsDto;
import com.cooksys.twitter_api.entities.Credentials;
import com.cooksys.twitter_api.entities.Tweet;
import com.cooksys.twitter_api.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByIdAndDeletedFalse(Long id);

	List<User> findAllByDeletedFalse();
	
	User findByCredentials_Username(String username);

	Optional<User> findByDeletedFalseAndCredentials(Credentials credentials);

	List<User> findAllByDeletedFalseAndTweetLikes(Tweet tweet);
}
