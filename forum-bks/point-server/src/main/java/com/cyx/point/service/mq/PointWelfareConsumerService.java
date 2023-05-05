package com.cyx.point.service.mq;

import com.cyx.common.msg.PointMsg;
import com.cyx.common.msg.PointWelfareMsg;
import com.cyx.common.utils.JsonUtil;
import com.cyx.point.service.PointService;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;

@Service
public class PointWelfareConsumerService {

    @Autowired
    PointService pointService;
//    监听积分赠送消息
    @RabbitListener(queues = "pointWelfareQueue")
    public void pointReceive(Message message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        String s = new String(message.getBody());
        PointWelfareMsg msg = JsonUtil.jsonStr2Object(s, PointWelfareMsg.class);
        Long memberId = msg.getMemberId();
        Long point = msg.getPoint();
        boolean res = pointService.addPoint(memberId, point);
        if(res){
            channel.basicAck(tag, false);
        }else{
            channel.basicNack(tag,false,false);
        }
    }
}
