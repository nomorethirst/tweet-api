package com.cooksys.tweetapi.dto;

import java.sql.Timestamp;

public class TweetDTO {

    private Integer id;

    private UserDTO author;

    private Timestamp posted;

    private String content;

    private TweetDTO inReplyTo;

    private TweetDTO repostOf;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserDTO getAuthor() {
        return author;
    }

    public void setAuthor(UserDTO author) {
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

    public TweetDTO getInReplyTo() {
        return inReplyTo;
    }

    public void setInReplyTo(TweetDTO inReplyTo) {
        this.inReplyTo = inReplyTo;
    }

    public TweetDTO getRepostOf() {
        return repostOf;
    }

    public void setRepostOf(TweetDTO repostOf) {
        this.repostOf = repostOf;
    }

    public boolean isValidSimple() {
        return id != null &&
                author != null && author.isValid() &&
                posted != null &&
                content != null;
    }

    public boolean isValidRepost() {
        return id != null &&
                author != null && author.isValid() &&
                posted != null &&
                repostOf != null; // &&
//                        (repostOf.isValidSimple() ||
//                            repostOf.isValidReply() ||
//                            repostOf.isValidRepost());
    }

    public boolean isValidReply() {
        return id != null &&
                author != null && author.isValid() &&
                posted != null &&
                content != null &&
                inReplyTo != null; // &&
//                        (inReplyTo.isValidSimple() ||
//                            inReplyTo.isValidReply() ||
//                            inReplyTo.isValidRepost());
    }


}
