package com.ntt.core.messaging;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Component
@Slf4j
@Getter
public class RedisMessageListener implements MessageListener {
    private final BlockingQueue<String> messageQueue = new LinkedBlockingQueue<>();

    @Override
    public void onMessage(Message message, byte[] pattern) {
        var operationSucceeded = messageQueue.offer(new String(message.getBody()));
        if (!operationSucceeded) {
            log.error("Could not retrieve queue message.");
        }
    }
}
