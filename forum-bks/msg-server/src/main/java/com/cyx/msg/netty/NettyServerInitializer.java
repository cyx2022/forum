package com.cyx.msg.netty;

import com.cyx.msg.netty.handler.MyHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class NettyServerInitializer {
    @Autowired
    private ServerInitializer serverInitializer;

    private EventLoopGroup master;
    private EventLoopGroup worker;
    private ServerBootstrap server;
    private ChannelFuture future;


    @PostConstruct
    public void start() throws InterruptedException {
        master=new NioEventLoopGroup();
        worker=new NioEventLoopGroup();
        server=new ServerBootstrap();


        server.option(ChannelOption.SO_BACKLOG, 1024);
        server.group(master, worker) // 绑定线程池
                .channel(NioServerSocketChannel.class) // 指定使用的channel
                .localAddress(8808)// 绑定监听端口
                .childHandler(serverInitializer);
        this.future=server.bind("127.0.0.1",8808).sync();
        if(future.isSuccess()){
            System.out.println("msg服务器启动成功");
        }
    }

    @PreDestroy
    public void destroy() throws InterruptedException {
        master.shutdownGracefully().sync();
        worker.shutdownGracefully().sync();
        System.out.println("服务器关闭");
    }

}
