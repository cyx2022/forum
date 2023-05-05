//package com.cyx.msg.service.mq;
//
//import com.rabbitmq.client.Channel;
//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.amqp.support.AmqpHeaders;
//import org.springframework.messaging.handler.annotation.Header;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//
//@Service
//public class PointWelfareMsgService {
//
////    监听积分赠送信息服务消息
//    @RabbitListener(queues = "pointWelfareMsgQueue")
//    public void pointWelfareMsgReceive(Message message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
//
//    }
//}
