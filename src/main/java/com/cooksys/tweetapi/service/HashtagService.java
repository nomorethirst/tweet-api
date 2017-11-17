package com.cooksys.tweetapi.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.cooksys.tweetapi.dto.HashtagDTO;
import com.cooksys.tweetapi.entity.Hashtag;
import com.cooksys.tweetapi.mapper.HashtagMapper;
import com.cooksys.tweetapi.repository.HashtagRepository;

@Service
public class HashtagService {

    private HashtagRepository hashtagRepository;

    private HashtagMapper hashtagMapper;

    public HashtagService(HashtagRepository hashtagRepository, HashtagMapper hashtagMapper) {
        this.hashtagRepository = hashtagRepository;
        this.hashtagMapper = hashtagMapper;
    }

    public List<HashtagDTO> getAllHashtags() {
        return hashtagRepository.findAll()
                .stream()
                .map(hashtagMapper::toDto)
                .collect(Collectors.toList());
    }

    public Hashtag getHashTag(String label) {
        return hashtagRepository.findByLabel(label);
    }

    public boolean hashtagExists(String label) {
        return hashtagRepository.findByLabel(label) != null;
    }

    @Transactional
    public Hashtag createHashtag(String label) {
        return hashtagRepository.save(new Hashtag(label));
    }

}
