package com.hometest.wechatapp.data;

/**
 * File name: Comments.java
 * Author: liwenlu 
 * Description: For Gson to parse comments data
 * 
 */

public class Comments {
	private String content;
	private Sender sender;
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	

	public Sender getSender() {
		return sender;
	}
	
	public void setSender(Sender sender) {
		this.sender = sender;
	}

	
}
