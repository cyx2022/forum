package com.cyx.main.service.mq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SenderService implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {
    @Value("${MQConfig.pointExchange}")
    private String pointExchange;

    @Autowired
    RabbitTemplate rabbitTemplate;

    public void send(Object object) {
        ObjectMapper mapper=new ObjectMapper();
        String msg = null;
        try {
            msg = mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        Message message= MessageBuilder.withBody(msg.getBytes())
                .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                .setMessageId(UUID.randomUUID()+"")
                .build();
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setConfirmCallback(this);
        CorrelationData correlationData=new CorrelationData(msg);
        rabbitTemplate.convertAndSend(pointExchange,null,message,correlationData);
    }


    public void sendDelay(String exchange,String route,Object object,int delayTime){
        ObjectMapper mapper=new ObjectMapper();
        String msg = null;
        if(object instanceof String){
            msg=(String)object;
        }else {
            try {
                msg = mapper.writeValueAsString(object);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        Message message=MessageBuilder.withBody(msg.getBytes())
                .setExpiration(String.valueOf(delayTime))
                .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                .setMessageId(UUID.randomUUID()+"")
                .build();
        CorrelationData correlationData=new CorrelationData(msg);
        rabbitTemplate.convertAndSend(exchange,route,message,correlationData);

    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        String id=correlationData.getId();
        if(ack){
            System.out.println("ok");
        }else{

            System.out.println("err");
        }
    }

    @Override
    public void returnedMessage(Message message, int i, String s, String s1, String s2) {
        System.out.println("return");
    }
}
