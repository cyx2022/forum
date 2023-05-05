package com.cyx.upload.config;

import com.cyx.upload.listener.KeyExpirationEventListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Redis配置类
 *
 * @author: LC
 * @date 2022/3/3 1:40 下午
 * @ClassName: RedisConfig
 */
@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        //key序列化
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //value序列化
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        //hash类型value序列化
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        //注入连接工厂
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        redisTemplate.afterPropertiesSet();

        // 添加Key过期事件监听器
        //用于监听redis图片的key的删除事件
        redisMessageListenerContainer(redisConnectionFactory).addMessageListener(keyExpirationEventMessageListener(redisMessageListenerContainer(redisConnectionFactory)), new PatternTopic("__keyevent@*__:del"));

        return redisTemplate;
    }

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory connectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        return container;
    }

    @Bean
    public KeyExpirationEventMessageListener keyExpirationEventMessageListener(RedisMessageListenerContainer listenerContainer) {
        return new KeyExpirationEventMessageListener(listenerContainer);
    }
//    @Bean
//    public KeyExpirationEventListener keyExpirationEventListener(RedisConnectionFactory connectionFactory){
//        return new KeyExpirationEventListener(redisMessageListenerContainer(connectionFactory));
//    }







}
