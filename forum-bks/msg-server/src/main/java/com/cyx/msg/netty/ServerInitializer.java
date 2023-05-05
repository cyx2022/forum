package com.cyx.msg.netty;

import com.cyx.msg.netty.handler.ChatHandler;
import com.cyx.msg.netty.handler.HeartBeatHandler;
import com.cyx.msg.netty.handler.MyHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@ChannelHandler.Sharable
@Component
public class ServerInitializer extends ChannelInitializer<SocketChannel> {

    @Resource
    private ChatHandler chatHandler;

    @Resource
    private HeartBeatHandler heartBeatHandler;

    @Autowired
    MyHandler myHandler;

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        ChannelPipeline pipeline=channel.pipeline();
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new ChunkedWriteHandler());
        pipeline.addLast(new HttpObjectAggregator(1024*64));
        pipeline.addLast(new IdleStateHandler(8,10,12));
        pipeline.addLast(myHandler);
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws", "WebSocket", true, 65536 * 10));
        pipeline.addLast(heartBeatHandler);
        pipeline.addLast(chatHandler);
    }
}
