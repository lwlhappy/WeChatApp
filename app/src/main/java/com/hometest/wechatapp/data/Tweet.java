package com.hometest.wechatapp.data;
/**
 * File name: Tweet.java
 * Author: liwenlu 
 * Description: For Gson to parse Tweet data
 * 
 */

import java.util.List;

public class Tweet {
	private String content;
	private List<Images> images ;
	private Sender sender;
	private List<Comments> comments;
	private String error;
	
	public String getContent () {
		return content;
	}
	
	public void setContent(String content) {
		this.content=content;
	}
	
	public List<Images> getImages () {
		return images;
	}
	
	public void setImages(List<Images> images) {
		this.images = images;
	}
	
	public Sender getSender () {
		return sender;
	}
	
	public void setSender (Sender sender) {
		this.sender = sender;
	}
	
	public List<Comments> getComments () {
		return comments;
	}
	
	public void setComments (List<Comments> comments) {
		this.comments = comments;
	}

	public String getEror() {
		return error;
	}
	
	public void setError(String error) {
		this.error = error;
	}
		
}