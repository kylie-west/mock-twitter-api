package com.cooksys.twitter_api.entities;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Table(name = "user_table")
@Data
public class User {

	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false, updatable = false)
	@CreationTimestamp
	private Timestamp joined;

	private boolean deleted = false;
	
	@Embedded
	private Profile profile;
	
	@Embedded
	private Credentials credentials;
	
	@ManyToMany
	@JoinTable(name = "followers_following", joinColumns = @JoinColumn(name = "follower_id"), inverseJoinColumns = @JoinColumn(name = "following_id"))
	List<User> followers;
	
	@ManyToMany(mappedBy = "followers")
	List<User> following;
	
	@ManyToMany(mappedBy = "likes")
	List<Tweet> tweetLikes;
	
	@ManyToMany(mappedBy = "mentions")
	List<Tweet> tweetMentions;

	@OneToMany(mappedBy="author")
	List<Tweet> tweets;
}
