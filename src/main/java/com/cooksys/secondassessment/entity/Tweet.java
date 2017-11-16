package com.cooksys.secondassessment.entity;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Tweet {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@ManyToOne
	private User author;
	
	private Timestamp posted;
	
	private String content;
	
	@OneToOne
	private Tweet inReplyTo;
	
	@OneToOne
	private Tweet repostOf;
	
	private Boolean deleted = false;
	
	@OneToMany
	private List<Tweet> replies;

	@OneToMany
	private List<Tweet> reposts;

	@ManyToMany
	private List<User> mentions;
	
	@OneToMany
	private List<User> likes;

	@OneToMany
	private List<Hashtag> tags;
	

	public Tweet() {}

	public Tweet(User author, Timestamp posted, String content) {
	    super();
	    this.author = author;
	    this.posted = posted;
	    this.content = content;
	}

	public Tweet(User author, Timestamp posted, String content, Tweet inReplyTo) {
	    super();
	    this.author = author;
	    this.posted = posted;
	    this.content = content;
	    this.inReplyTo = inReplyTo;
	}

	public Tweet(User author, Timestamp posted, Tweet repostOf) {
	    super();
	    this.author = author;
	    this.posted = posted;
	    this.repostOf = repostOf;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public Timestamp getPosted() {
		return posted;
	}

	public void setPosted(Timestamp posted) {
		this.posted = posted;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Tweet getInReplyTo() {
		return inReplyTo;
	}

	public void setInReplyTo(Tweet inReplyTo) {
		this.inReplyTo = inReplyTo;
	}

	public Tweet getRepostOf() {
		return repostOf;
	}

	public void setRepostOf(Tweet repostOf) {
		this.repostOf = repostOf;
	}

	public Boolean getDeleted() {
	    return deleted;
	}

	public void setDeleted(Boolean deleted) {
	    this.deleted = deleted;
	}

	public List<Tweet> getReplies() {
	    return replies;
	}

	public void setReplies(List<Tweet> replies) {
	    this.replies = replies;
	}

	public List<Tweet> getReposts() {
	    return reposts;
	}

	public void setReposts(List<Tweet> reposts) {
	    this.reposts = reposts;
	}

	public List<User> getMentions() {
	    return mentions;
	}

	public void setMentions(List<User> mentions) {
	    this.mentions = mentions;
	}

	public List<User> getLikes() {
	    return likes;
	}

	public void setLikes(List<User> likes) {
	    this.likes = likes;
	}

	public List<Hashtag> getTags() {
	    return tags;
	}

	public void setTags(List<Hashtag> tags) {
	    this.tags = tags;
	}
	
	public boolean hasInReplyTo() {
	    return inReplyTo != null;
	}
	
	public boolean hasReplies() {
	    return !replies.isEmpty();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tweet other = (Tweet) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	

}
