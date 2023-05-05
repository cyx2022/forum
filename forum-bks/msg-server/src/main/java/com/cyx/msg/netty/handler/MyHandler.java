package com.cyx.msg.netty.handler;

import com.cyx.common.utils.JsonUtil;
import com.cyx.common.utils.JwtUtil;
import com.cyx.msg.holder.MemberIdHolder;
import com.cyx.msg.netty.data.ChatMsg;
import com.cyx.msg.netty.data.DataContext;
import com.cyx.msg.netty.enums.MsgActionEnum;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Component
@ChannelHandler.Sharable
public class MyHandler extends ChannelInboundHandlerAdapter {
    public Map<Channel,String> tokenMap=new HashMap<>();
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof HttpRequest){
            HttpHeaders headers = ((HttpRequest) msg).headers();
            String memberToken = headers.get("memberToken");
            if(memberToken!=null){
                tokenMap.put(ctx.channel(),memberToken);
            }else{
//                连接没有经过网关
                DataContext dataContext=new DataContext();
                dataContext.setAction(MsgActionEnum.ERROR.type);
                String s = JsonUtil.object2JsonStr(dataContext);
                ctx.writeAndFlush(s);
                ctx.close();
            }
        }else if(msg instanceof TextWebSocketFrame){
//            保存memberId
            String access_token = tokenMap.get(ctx.channel());
            if(access_token==null){
//                token为空
                DataContext dataContext=new DataContext();
                dataContext.setAction(MsgActionEnum.TOKENILLEGAL.type);
                String s = JsonUtil.object2JsonStr(dataContext);
                ctx.writeAndFlush(s);
                ctx.close();
            }
            try {
                Long memberId = Long.valueOf(JwtUtil.resolveToken(access_token));
                MemberIdHolder.setMemberId(memberId);
            }catch(Exception e){
//                token异常
                DataContext dataContext=new DataContext();
                dataContext.setAction(MsgActionEnum.TOKENILLEGAL.type);
                String s = JsonUtil.object2JsonStr(dataContext);
                ctx.writeAndFlush(s);
                ctx.close();
            }
        }
        ctx.fireChannelRead(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
