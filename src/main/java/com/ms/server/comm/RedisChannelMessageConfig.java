package com.ms.server.comm;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;


@Configuration
public class RedisChannelMessageConfig {


    //利用反射来创建监听到消息之后的执行方法
    @Bean
    MessageListenerAdapter listenerAdapter() {

        return new MessageListenerAdapter(new RedisChannelListener());
    }

    //初始化监听器
    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);

        container.addMessageListener(listenerAdapter, new PatternTopic("channel1"));

        return container;
    }


}