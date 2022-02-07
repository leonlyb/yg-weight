package com.example.dechuan.model.usermanage;

import java.io.Serializable;

public class User implements Serializable{
	
	private String userName;
	private String password;
	private Integer ususid;

	public User(String userName, String password) {
		this.userName=userName;
		this.password=password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getUsusid() {
		return ususid;
	}

	public void setUsusid(Integer ususid) {
		this.ususid = ususid;
	}


	
	

}
