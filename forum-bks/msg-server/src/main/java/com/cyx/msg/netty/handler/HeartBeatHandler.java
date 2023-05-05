package com.cyx.msg.netty.handler;

import io.netty.channel.*;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.springframework.stereotype.Component;

@Component
@ChannelHandler.Sharable
public class HeartBeatHandler extends ChannelInboundHandlerAdapter {
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt){
        if(evt instanceof IdleStateEvent){
            IdleStateEvent event=(IdleStateEvent)evt;
            if(event.state()== IdleState.READER_IDLE){
                System.out.println("读空闲");
            }else if(event.state()==IdleState.WRITER_IDLE){
                System.out.println("写空闲");
            }else if(event.state()==IdleState.ALL_IDLE){
                System.out.println(ChatHandler.members.size());
                System.out.println(ctx.channel().localAddress());
                System.out.println("读写空闲，需要关闭连接");
                Channel channel=ctx.channel();
                channel.close();
            }
        }
    }
}
