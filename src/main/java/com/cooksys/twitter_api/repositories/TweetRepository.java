package com.cooksys.twitter_api.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cooksys.twitter_api.dtos.TweetResponseDto;
import com.cooksys.twitter_api.entities.Tweet;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long> {
	
	Optional<Tweet> findByIdAndDeletedFalse(Long id);
	
	List<Tweet> findAllByDeletedFalse();
	
	List<Tweet> findAllByDeletedFalseAndAuthor_Credentials_Username(String username);

	List<TweetResponseDto> findAllByDeletedFalseAndHashtags_Label(String label);

	List<Tweet> findAllByDeletedFalseAndInReplyTo(Long id);
	
}
