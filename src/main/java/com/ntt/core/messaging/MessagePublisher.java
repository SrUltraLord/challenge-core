package com.ntt.core.messaging;

public interface MessagePublisher {
    void publish(String channel, String message);
}
