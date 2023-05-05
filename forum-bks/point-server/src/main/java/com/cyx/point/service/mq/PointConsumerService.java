package com.cyx.point.service.mq;

import com.alibaba.fastjson.TypeReference;
import com.cyx.common.entity.Comment;
import com.cyx.common.entity.SubComment;
import com.cyx.common.msg.PointMsg;
import com.cyx.common.utils.JsonUtil;
import com.cyx.point.constant.RedisHeaderConstant;
import com.cyx.point.constant.RedisValueConstant;
import com.cyx.point.feign.MainFeignService;
import com.cyx.point.service.AddPointService;
import com.cyx.point.service.PointService;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class PointConsumerService {

    @Autowired
    MainFeignService mainFeignService;

    @Autowired
    PointService pointService;

    @Autowired
    AddPointService addPointService;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    @Qualifier(value = "pointMaxScript")
    private RedisScript<Boolean> pointMaxScript;

    @RabbitListener(queues = "pointQueue")
    public void pointReceive(Message message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        String s = new String(message.getBody());
        PointMsg msg = JsonUtil.jsonStr2Object(s, PointMsg.class);
        Long commentId = msg.getCommentId();
        Long subCommentId = msg.getSubCommentId();
        Date publicTime = msg.getPublicTime();
        Long memberId = msg.getMemberId();
        Long point = msg.getPoint();

        boolean signal = check(memberId);
//            每天获取的积分已经到达上限
        if(!signal){
            return;
        }
//        判重
        boolean flag = addPointService.add(commentId, subCommentId, publicTime);
        if (flag != true) {
//            之前已经消费消息
            return;
        }
//        检查发送端是否由于异常导致事务回滚,分两种情况，一级评论和二级评论，按照id为 -1 区分
        Comment comment = null;
        SubComment subComment = null;
        if (commentId != -1) {
            comment = mainFeignService.getComment(commentId).getObject(new TypeReference<Comment>() {
            });
        } else {
            subComment = mainFeignService.getSubComment(subCommentId).getObject(new TypeReference<SubComment>() {
            });
        }

        if (comment != null || subComment != null) {
            boolean res = pointService.addPoint(memberId, point);
            if (res)
                channel.basicAck(tag, false);
            else {
                channel.basicNack(tag, false, true);
            }
        } else {
//            记录日志
        }
    }

    public boolean check(long memberId){
//        判断创建时间，如果是前一天，删除，重建，否则++
        String key = RedisHeaderConstant.getPointMaxKey(memberId);
        String previous=RedisHeaderConstant.getPointMaxPrevious(memberId);
        long max=RedisValueConstant.POINT_MAX;
        List<String> list=new ArrayList<>();
        list.add(key);
        list.add(previous);
        list.add(max+"");
        list.add(60*60*24+"");
        boolean res = (Boolean) redisTemplate.execute(pointMaxScript,list, Collections.EMPTY_LIST);
        return res;
    }
}
