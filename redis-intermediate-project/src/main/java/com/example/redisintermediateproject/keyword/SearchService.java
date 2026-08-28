package com.example.redisintermediateproject.keyword;

import lombok.RequiredArgsConstructor;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final SearchRepository searchRepository;
    private final RedisTemplate<String, String> redisTemplate;

    @Transactional
    public void search(String keyword) {
        SearchKeyword searchKeyword = searchRepository.findByKeyword(keyword)
                .orElse(new SearchKeyword(keyword));
        searchKeyword.increaseCount();
        searchRepository.save(searchKeyword);
    }

    @Transactional(readOnly = true)
    public List<String> getTop10Keywords() {
        return searchRepository.findTop10ByOrderByCountDesc().stream()
                .map(SearchKeyword::getKeyword)
                .toList();
    }

    public void searchWithRedis(String keyword) {
        redisTemplate
            .opsForZSet()
            .incrementScore("search_keywords_ranking", keyword, 1.0);
    }

    public List<String> getTop10KeywordsWithRedis() {
        Set<String> keywords = redisTemplate
            .opsForZSet()
            .reverseRange("search_keywords_ranking", 0, 9);
        return new ArrayList<>(keywords);
            
    }
}
