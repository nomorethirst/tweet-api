package com.cooksys.secondassessment.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.secondassessment.dto.HashtagDTO;
import com.cooksys.secondassessment.service.HashtagService;

@RestController
@RequestMapping("tags")
public class HashtagController {

	private HashtagService hashtagService;
	
	public HashtagController(HashtagService hashtagService) {
		this.hashtagService = hashtagService;
	}
	
	@GetMapping
	public List<HashtagDTO> getHashtags() {
		return hashtagService.getAllHashtags();
	}
	
//	@GetMapping("/tags/{label}")
//	public HashtagDTO getHashtag(@PathVariable String label) {
//		
//	}

}
