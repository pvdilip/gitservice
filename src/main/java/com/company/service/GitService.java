package com.company.service;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.company.model.GitFollwers;

@Service
public class GitService {
	
	@Autowired
    private RestTemplate restTemplate;

	private int count = 0;
    // inject value from application.properties
    @Value("${api.url}")
    private String API_URL;
    
    @Value("${user.id}")
    private String USER_ID;
    
    @Value("${followers.url}")
    private String FOLLOWERS_URL;

   
    public JSONObject getFollwers(String gitid)
    {
    	ResponseEntity<List<GitFollwers>> gitUserResponse = getGitFollwers(API_URL+gitid+FOLLOWERS_URL+"?per_page=5");    	      
    	List<GitFollwers> gitFollwers = gitUserResponse.getBody(); 
    	
    	JSONObject object = new JSONObject();
    	object.put("USER_ID", gitid);       
    	JSONObject objectSub = new JSONObject();
    	for(GitFollwers gitFollwer : gitFollwers) {
    		System.out.println("follwer url:: "+gitFollwer.getFollowersUrl());
    		getGitFollwersData(gitFollwer.getFollowersUrl()+"?per_page=5", objectSub);   
    		break;
    	}
    	object.put("gitFollwers", objectSub);
        return object;
    }
    
    private void getGitFollwersData(String url, JSONObject objectSub){
    	ResponseEntity<List<GitFollwers>> gitFollwerResponse = getGitFollwers(url);    	      
    	List<GitFollwers> gitFollwersList = gitFollwerResponse.getBody(); 
		
		  JSONArray list = new JSONArray(); 
		  for(GitFollwers gitFollwer : gitFollwersList) {
			  System.out.println("follwer url2:: "+gitFollwer.getFollowersUrl());
			  getGitFollwersData(gitFollwer.getFollowersUrl()+"?per_page=5", objectSub);
			  list.put(gitFollwersList); 
			  objectSub.append("list", gitFollwersList);    
			  break;
		  }
		 
    	objectSub.append("list", gitFollwersList);    	
    }
    
    private ResponseEntity<List<GitFollwers>> getGitFollwers(String url){
    	System.out.println("url:: "+url);
    	ResponseEntity<List<GitFollwers>> gitResponse =
    	        restTemplate.exchange(url,
    	                    HttpMethod.GET, null, new ParameterizedTypeReference<List<GitFollwers>>() {
    	            });
    	return gitResponse;
    }
}
