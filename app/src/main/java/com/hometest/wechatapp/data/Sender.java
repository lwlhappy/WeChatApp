package com.hometest.wechatapp.data;

/**
 * File name: Sender.java
 * Author: liwenlu 
 * Description: For Gson to parse Sender data
 * 
 */
public class Sender {
	private String avatar;
	 private String nick;
	 private String username;
	 
	 
	 public String getAvatar () {
		 return avatar;
	 }
	 
	 public void setAvatar (String avatar) {
		this.avatar = avatar; 
	 }
	 
	 public String getNick () {
		 return nick;
	 }
	 
	 public void setNick (String nick) {
		this.nick = nick; 
	 }
	 
	 public String getUserName () {
		 return username;
	 }
	 
	 public void setUserName (String username) {
		this.username = username; 
	 }
		
}
