package com.cyx.point.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

}
