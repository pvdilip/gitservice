package com.company.model;

public class GitServiceResponse {
	
	String user;
	GitFollowersData data;

	

	public GitFollowersData getData() {
		return data;
	}

	public void setData(GitFollowersData data) {
		this.data = data;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
	
	
}
