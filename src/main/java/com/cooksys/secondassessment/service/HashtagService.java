package com.cooksys.secondassessment.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.cooksys.secondassessment.dto.HashtagDTO;
import com.cooksys.secondassessment.mapper.HashtagMapper;
import com.cooksys.secondassessment.repository.HashtagRepository;

@Service
public class HashtagService {
	
	private HashtagRepository hashtagRepository;
	
	private HashtagMapper hashtagMapper;

	public HashtagService(HashtagRepository hashtagRepository, 
			HashtagMapper hashtagMapper) {
		this.hashtagRepository = hashtagRepository;
		this.hashtagMapper = hashtagMapper;
	}

	public List<HashtagDTO> getAllHashtags() {
		return hashtagRepository.findAll()
				.stream()
				.map(hashtagMapper::toDto)
				.collect(Collectors.toList());
	}
	
	public boolean hashtagExists(String label) {
		return hashtagRepository.findByLabel(label) != null;
	}
	
	
	
	

}
