package com.company.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.company.service.GitService;

@RequestMapping(value = "/git-api")
@RestController
public class GitServiceController {

	@Autowired
	private GitService gitService;

	@RequestMapping(value = "/followers/{gitid}", method = RequestMethod.GET)	
	public ResponseEntity<String> getFollowerDetails(@PathVariable("gitid") String gitid){
		JSONObject response = gitService.getFollwers(gitid);
		return new ResponseEntity<String>(response.toString(), HttpStatus.OK);
	}
	
}
