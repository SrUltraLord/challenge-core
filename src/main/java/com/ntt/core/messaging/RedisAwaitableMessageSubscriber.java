package com.ntt.core.messaging;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RedisAwaitableMessageSubscriber implements AwaitableMessageSubscriber {
    private static final long TIMEOUT_IN_SECONDS = 5L;

    private final StringRedisTemplate redisTemplate;
    private final RedisMessageListener listener;

    @Override
    public void subscribe(String channel) {
        var connectionFactory = redisTemplate.getConnectionFactory();
        if (connectionFactory == null) return;

        connectionFactory.getConnection()
                .subscribe(listener, channel.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String awaitForMessage() throws InterruptedException {
        return listener.getMessageQueue().poll(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS);
    }
}
