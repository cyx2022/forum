//package com.cyx.msg.netty.config;
//
//import io.netty.handler.codec.http.HttpObjectAggregator;
//import io.netty.handler.codec.http.HttpServerCodec;
//import io.netty.handler.stream.ChunkedWriteHandler;
//import io.netty.handler.timeout.IdleStateHandler;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class NettyConfig {
//
//    @Bean
//    public HttpServerCodec httpServerCodec() {
//        return new HttpServerCodec();
//    }
//
//    @Bean
//    public ChunkedWriteHandler chunkedWriteHandler() {
//        return new ChunkedWriteHandler();
//    }
//
//    @Bean
//    public HttpObjectAggregator httpObjectAggregator() {
//        return new HttpObjectAggregator(1024 * 64);
//    }
//
//    @Bean
//    public IdleStateHandler idleStateHandler() {
//        return new IdleStateHandler(8, 10, 12);
//    }
//
//
//}
