package com.hometest.wechatapp.data;
/**
 * File name: Tweet.java
 * Author: liwenlu 
 * Description: For Gson to parse User data
 * 
 */
import com.google.gson.annotations.SerializedName;

public class User {
	@SerializedName("profile-image")
	 private String profieimage;
	 private String avatar;
	 private String nick;
	 private String username;
	 
	 public String getProfieImage () {
		 return profieimage;
	 }
	 
	 public void setsetprofieimage (String profieimage) {
		this.profieimage = profieimage; 
	 }
	 
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
