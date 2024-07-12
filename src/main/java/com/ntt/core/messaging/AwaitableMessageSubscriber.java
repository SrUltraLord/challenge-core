package com.ntt.core.messaging;

public interface AwaitableMessageSubscriber {
    void subscribe(String channel);

    String awaitForMessage() throws InterruptedException;
}
