package com.ntt.core.messaging;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisMessagePublisher implements MessagePublisher {
    private final StringRedisTemplate template;

    @Override
    public void publish(String channel, String message) {
        template.convertAndSend(channel, message);
    }
}
