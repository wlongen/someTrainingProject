package com.wenlong.rinetd.entity;

public class UserInDB {
	private String username;
	private String pwd;

	public UserInDB(String username, String pwd) {
		super();
		this.username = username;
		this.pwd = pwd;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	@Override
	public String toString() {
		return "UserInDB [username=" + username + ", pwd=" + pwd + "]";
	}

}
