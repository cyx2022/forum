//package com.cyx.msg.netty.config;
//
//import com.cyx.msg.netty.handler.MyHandler;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.socket.config.annotation.EnableWebSocket;
//import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
//import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
//
//@EnableWebSocket
//@Configuration
//public class WebSocketConfig implements WebSocketConfigurer {
//    @Autowired
//    MyHandler myHandler;
//
//    @Override
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//        // 注册 WebSocket 处理程序和路径
//        registry.addHandler(myHandler, "/ws").setAllowedOrigins("*");
//    }
//}
