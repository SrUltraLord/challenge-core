package com.ntt.core.config;

import com.ntt.core.messaging.RedisMessageListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Configuration
public class RedisConfiguration {
    @Bean
    public RedisMessageListenerContainer container(
            RedisConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
        var container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapter, new ChannelTopic("response:*"));

        return container;
    }

    @Bean
    public MessageListenerAdapter listenerAdapter(RedisMessageListener listener) {
        return new MessageListenerAdapter(listener, "onMessage");
    }

    @Bean
    public RedisMessageListener listener() {
        return new RedisMessageListener();
    }
}
