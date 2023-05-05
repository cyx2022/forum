package com.cyx.main.listener;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

//对删除操作的延时双删
@Component
public class DelayQueueConsumer {
    @Autowired
    RedisTemplate redisTemplate;

    @RabbitListener(queues = "contentDelayQueue")
    public void contentDelayQueue(Channel channel, Message message, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        String key = new String(message.getBody());
        redisTemplate.delete(key);
        channel.basicAck(tag, false);
    }

    @RabbitListener(queues = "barDelayQueue")
    public void barDelayQueue(Channel channel,Message message,@Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        String key = new String(message.getBody());
        redisTemplate.delete(key);
        channel.basicAck(tag, false);
    }

}
