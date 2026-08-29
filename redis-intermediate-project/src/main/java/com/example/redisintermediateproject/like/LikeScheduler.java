package com.example.redisintermediateproject.like;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// @Component
@RequiredArgsConstructor
@Slf4j
public class LikeScheduler {
    private final RedisTemplate<String, String> redisTemplate;
    private final LikeRepository likeRepository;

    @Scheduled(fixedDelay = 1000)
    public void saveLikesToDb(){
        List<Like> likeToSave = new ArrayList<>();
        while(true){
            String value = redisTemplate.opsForList().leftPop("like_queue");
            if(value == null){
                break;
            }
            // userId:1:postId:1
            String[] values = value.split(":");
            Long userId = Long.parseLong(values[1]);
            Long postId = Long.parseLong(values[3]);
            likeToSave.add(new Like(userId, postId));

            if(likeToSave.size() == 1000){
                break;
            }
        }

        likeRepository.saveAll(likeToSave);
        log.info("DB 저장 완료 {} 건", likeToSave.size());
    }
}
