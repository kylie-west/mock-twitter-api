package com.cooksys.twitter_api.entities;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class Tweet {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "user_table_id", nullable = false)
	private User author;
	
	@Column(nullable = false)
	@CreationTimestamp
	private Timestamp posted;
	
	@Column(nullable = false)
	private boolean deleted;
	
	private String content;
	
	@ManyToOne
	@JoinColumn(name = "reply_to_fk_id")
	private Tweet inReplyTo;
	
	@OneToMany(mappedBy = "inReplyTo")
    	private List<Tweet> replies = new ArrayList<>();
	
	@ManyToOne
	@JoinColumn(name = "repost_of_fk_id")
	private Tweet repostOf;
	
	@OneToMany(mappedBy = "repostOf")
    	private List<Tweet> reposts = new ArrayList<>();
	
	@ManyToMany
	@JoinTable(name = "tweet_hashtags", joinColumns = @JoinColumn(name = "tweet_id"), inverseJoinColumns = @JoinColumn(name = "hashtag_id"))
	List<Hashtag> hashtags = new ArrayList<>();;
	
	@ManyToMany
	@JoinTable(name = "user_likes", joinColumns = @JoinColumn(name = "tweet_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	List<User> likes = new ArrayList<>();;
	
	@ManyToMany
	@JoinTable(name = "user_mentions", joinColumns = @JoinColumn(name = "tweet_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	List<User> mentions = new ArrayList<>();;
}
