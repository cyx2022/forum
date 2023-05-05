package com.cyx.msg.netty.handler;

import cn.hutool.json.JSONUtil;
import com.cyx.common.utils.JsonUtil;
import com.cyx.msg.entity.Msg;
import com.cyx.msg.holder.MemberIdHolder;
import com.cyx.msg.netty.data.ChatMsg;
import com.cyx.msg.netty.data.DataContext;
import com.cyx.msg.netty.data.MemberChannelRel;
import com.cyx.msg.netty.enums.MsgActionEnum;
import com.cyx.msg.service.MsgService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Sharable
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    public static ChannelGroup members=new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Autowired
    MsgService msgService;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        String context=msg.text();
        DataContext dataContext= JsonUtil.jsonStr2Object(context,DataContext.class);
        Integer action = dataContext.getAction();
        Channel channel=ctx.channel();
        if(action== MsgActionEnum.CONNECT.type){
            long memberId=MemberIdHolder.getMemberId();
            MemberChannelRel.put(memberId,channel);
            System.out.println("客户端" + channel.id().asShortText() + "连接成功");
        }else if(action==MsgActionEnum.CHAT.type){
            ChatMsg chatMsg=dataContext.getChatMsg();
            String msgContext=chatMsg.getMsg();
            int senderId=chatMsg.getSenderId();
            int receiverId=chatMsg.getReceiverId();
//          保持到数据库，并设置状态为未签收,获得保持的id，给接收者回传消息需要传递该id，接收者需要回传该id，表示已签收
            Msg dbMsg=new Msg();
            dbMsg.setSenderId(chatMsg.getSenderId());
            dbMsg.setReceiverId(chatMsg.getReceiverId());
            dbMsg.setContent(chatMsg.getMsg());
            dbMsg.setFlag(0);
            dbMsg.setSign(0);
            dbMsg.setTime(new Date());
            dbMsg.setTitle("");
            msgService.save(dbMsg);
            Channel receiverChannel=MemberChannelRel.get(receiverId);
//          为服务端接收到的msg设置上面得到的msg存储id
            chatMsg.setMsgId(dbMsg.getId());
            DataContext rtDataContext=new DataContext();
            rtDataContext.setChatMsg(chatMsg);
            if(receiverChannel==null){
                // TODO 用户离线
            }else {
                Channel findChannel = members.find(receiverChannel.id());
                if (findChannel != null) {
                    // TODO 回传消息
                    receiverChannel.writeAndFlush(new TextWebSocketFrame(
                            JsonUtil.object2JsonStr(dataContext)
                    ));
                } else {
                    // TODO 用户离线
                }
            }

        }else if(action==MsgActionEnum.SIGNED.type){
            String msgIdStr=dataContext.getExtend();
            String[] msgIds=msgIdStr.split(",");
            List<String> list=new ArrayList<>();
            for (String id : msgIds) {
                if(!StringUtils.isEmpty(id)){
                    list.add(id);
                }
            }

            if(list!=null&&!list.isEmpty()&&list.size()>0){
                msgService.signBatchMsg(list);
                System.out.println("签收消息"+list.toString());
            }
        }else if(action==MsgActionEnum.KEEPALIVE.type){
//            System.out.println("接收到" + channel + "的心跳包");
        }
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx){
        members.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx){
        System.out.println(members.size());
        System.out.println("客户端" + ctx.channel().id().asShortText() + "离线");
        members.remove(ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause){
        System.out.println("客户端" + ctx.channel().id().asShortText() + "发生异常");
        cause.printStackTrace();
        ctx.channel().close();
        members.remove(ctx.channel());
    }


}
