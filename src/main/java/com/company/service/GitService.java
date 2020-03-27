package com.company.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
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
    	List<String> follwersUrls = new ArrayList<>();
    	JSONObject object = new JSONObject();
    	object.put("USER_ID", gitid);       
    	JSONObject objectSub = new JSONObject();
    	for(GitFollwers gitFollwer : gitFollwers) {
    		System.out.println("follwer url:: "+gitFollwer.getFollowersUrl());
    		follwersUrls.add(gitFollwer.getFollowersUrl());
    		objectSub.append("list", getGitFollwersData(gitFollwer.getFollowersUrl()+"?per_page=5"));
    	}
    	System.out.println("follwersUrls size:: "+follwersUrls.size());
    	//get follower data
    	List<String> follwers1Urls = new ArrayList<>();
    	for(String gitFollwer : follwersUrls) {
    		System.out.println("follwer url1:: "+gitFollwer);    		;	
    		objectSub.append("list", getGitFollwersData(gitFollwer+"?per_page=5"));    		
    		for(GitFollwers gitFollwer1: gitFollwers) {
        		System.out.println("follwer url:: "+gitFollwer1.getFollowersUrl());
        		follwers1Urls.add(gitFollwer1.getFollowersUrl());        		
        	}    		
    	}    	
    	System.out.println("follwers1Urls size:: "+follwers1Urls.size());
    	
    	//get follower data
    	List<String> follwers2Urls = new ArrayList<>();
    	for(String gitFollwer : follwers1Urls) {
    		System.out.println("follwer url2:: "+gitFollwer);
    		objectSub.append("list", getGitFollwersData(gitFollwer+"?per_page=5"));    
    		for(GitFollwers gitFollwer2: gitFollwers) {
        		System.out.println("follwer url:: "+gitFollwer2.getFollowersUrl());
        		follwers2Urls.add(gitFollwer2.getFollowersUrl());        		
        	}    		
    	}    	
    	System.out.println("follwers2Urls size:: "+follwers2Urls.size());
    	
    	//get follower data    	
    	for(String gitFollwer : follwers2Urls) {
    		System.out.println("follwer url2:: "+gitFollwer);
    		objectSub.append("list", getGitFollwersData(gitFollwer+"?per_page=5"));    		  		
    	}
    	
    	object.put("gitFollwers", objectSub);
        return object;
    }
    
    private List<GitFollwers> getGitFollwersData(String url){
    	ResponseEntity<List<GitFollwers>> gitFollwerResponse = getGitFollwers(url);    	      
    	List<GitFollwers> gitFollwersList = gitFollwerResponse.getBody(); 		
	    return gitFollwersList;
    }
    
    private ResponseEntity<List<GitFollwers>> getGitFollwers(String url){
    	System.out.println("url:: "+url);
    	HttpEntity<String> request = new HttpEntity<String>(createHeaders());
    	ResponseEntity<List<GitFollwers>> gitResponse =
    	        restTemplate.exchange(url,
    	                    HttpMethod.GET, request, new ParameterizedTypeReference<List<GitFollwers>>() {
    	            });
    	return gitResponse;
    }
    
    HttpHeaders createHeaders(){
    	String plainCreds = "Iv1.5b4cf6e9b6782cec:9977d7ed9be96664c58ba0cf36c366adbe49872c";
    	byte[] plainCredsBytes = plainCreds.getBytes();
    	byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
    	String base64Creds = new String(base64CredsBytes);

    	HttpHeaders headers = new HttpHeaders();
    	headers.add("Authorization", "token " + "90168c33418713ce7ff91d319a02b65153422d95");
    	return headers;
    }
}
