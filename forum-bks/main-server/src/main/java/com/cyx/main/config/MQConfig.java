package com.cyx.main.config;


import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class MQConfig {
    //    评论新增积分
    @Value("${MQConfig.pointQueue}")
    private String pointQueue;
    @Value("${MQConfig.pointExchange}")
    private String pointExchange;
    @Value("${MQConfig.pointRoute}")
    private String pointRoute;


    @Bean
    public Queue pointQueue() {
        return new Queue(pointQueue, false, false, false);
    }

    @Bean
    public FanoutExchange pointExchange() {
        return new FanoutExchange(pointExchange, false, false);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(pointQueue()).to(pointExchange());
    }

    //    活动新增积分
    @Value("${MQConfig.pointWelfareQueue}")
    private String pointWelfareQueue;

    @Value("${MQConfig.pointWelfareMsgQueue}")
    private String pointWelfareMsgQueue;

    @Value("${MQConfig.pointWelfareExchange}")
    private String pointWelfareExchange;
    @Value("${MQConfig.pointWelfareRoute}")
    private String pointWelfareRoute;

    //    新增积分
    @Bean
    public Queue pointWelfareQueue() {
        return new Queue(pointWelfareQueue, false, false, false);
    }

    //    发送消息
    @Bean
    public Queue pointWelfareMsgQueue() {
        return new Queue(pointWelfareMsgQueue, false, false, false);
    }

    @Bean
    public FanoutExchange pointWelfareExchange() {
        return new FanoutExchange(pointWelfareExchange, false, false);
    }

    @Bean
    public Binding pointWelfareBinding() {
        return BindingBuilder.bind(pointWelfareQueue()).to(pointWelfareExchange());
    }

    //       延时双删
    @Value("${MQConfig.barDelayQueue}")
    private String barDelayQueue;

    @Value("${MQConfig.contentDelayQueue}")
    private String contentDelayQueue;
    @Value("${MQConfig.delayExchange}")
    private String delayExchange;

    @Value("${MQConfig.contentDelayRoute}")
    private String contentDelayRoute;

    @Value("${MQConfig.barDelayRoute}")
    private String barDelayRoute;

    @Bean
    public Queue barDelayQueue() {
        return new Queue(barDelayQueue, false, false, false);
    }

    @Bean
    public Queue contentDelayQueue() {
        return new Queue(contentDelayQueue, false, false, false);
    }

    @Bean("delayExchange")
    public CustomExchange delayedExchange() {
        Map<String, Object> args = new HashMap<>();
        //自定义交换机的类型，指定分发方式
        args.put("x-delayed-type", "direct");
        //此处type指定为延迟交换机
        return new CustomExchange(delayExchange, "x-delayed-message", true, false, args);
    }

    @Bean
    public Binding bindingBarDelayedQueue(@Qualifier("barDelayQueue") Queue queue,
                                          @Qualifier("delayExchange") CustomExchange delayedExchange) {
        return BindingBuilder.bind(queue).to(delayedExchange).with(barDelayRoute).noargs();
    }

    @Bean
    public Binding bindingContentDelayedQueue(@Qualifier("contentDelayQueue") Queue queue,
                                              @Qualifier("delayExchange") CustomExchange delayedExchange) {
        return BindingBuilder.bind(queue).to(delayedExchange).with(contentDelayRoute).noargs();
    }


}
