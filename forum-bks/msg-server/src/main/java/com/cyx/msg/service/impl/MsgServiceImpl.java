package com.cyx.msg.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyx.msg.entity.Msg;
import com.cyx.msg.holder.MemberIdHolder;
import com.cyx.msg.mapper.MsgMapper;
import com.cyx.msg.service.MsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class MsgServiceImpl extends ServiceImpl<MsgMapper, Msg> implements MsgService {
    @Resource
    MsgMapper msgMapper;

    @Override
    public long getSystemMsgCount() {
        long memberId = MemberIdHolder.getMemberId();
        QueryWrapper<Msg> wrapper = new QueryWrapper<>();
        wrapper.eq("sender_id", 0);
        wrapper.eq("receiver_id", memberId);
        wrapper.eq("sign", 0);
        long count = count(wrapper);
        return count;
    }

    @Override
    public long getPersonMsgCountAll() {
        long memberId = MemberIdHolder.getMemberId();
        QueryWrapper<Msg> wrapper = new QueryWrapper<>();
        wrapper.ne("sender_id", 0);
        wrapper.eq("receiver_id", memberId);
        wrapper.eq("sign", 0);
        long count = count(wrapper);
        return count;
    }

    @Override
    public List<Msg> getSystemMsgUnreadList() {
        long memberId = MemberIdHolder.getMemberId();
        QueryWrapper<Msg> wrapper = new QueryWrapper<>();
        wrapper.eq("sender_id", 0);
        wrapper.eq("receiver_id", memberId);
        wrapper.ge("time", get30preDate());
        List<Msg> list = list(wrapper);
        return list;
    }

    private Date get30preDate() {
        Date date = new Date();
        // 创建 Calendar 实例并设置日期
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // 将日期减一天
        calendar.add(Calendar.DAY_OF_MONTH, -30);
        Date time = calendar.getTime();
        return time;
    }

    @Override
    public List<Msg> getSystemMsgLatestList() {
        long memberId = MemberIdHolder.getMemberId();
        QueryWrapper<Msg> wrapper = new QueryWrapper<>();
        Date date = new Date();
        // 创建 Calendar 实例并设置日期
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // 将日期减一天
        calendar.add(Calendar.DAY_OF_MONTH, -30);
        Date time = calendar.getTime();
        wrapper.le("time", time);
        wrapper.eq("sender_id", 0);
        wrapper.eq("receiver_id", memberId);
        wrapper.eq("sign", 1);
        List<Msg> list = list(wrapper);
        return list;
    }

    @Override
    public void signBatchMsg(List<String> list) {
        msgMapper.signBatchMsg(list);
    }

    @Override
    public List<Msg> getPersonMsgList(long senderId) {
        long memberId = MemberIdHolder.getMemberId();
        QueryWrapper<Msg> wrapper = new QueryWrapper<>();
        wrapper.ge("time", get30preDate());
        wrapper.and(w -> w
                .or(ww -> ww.eq("sender_id", senderId).eq("receiver_id", memberId))
                .or(ww -> ww.eq("sender_id", memberId).eq("receiver_id", senderId))
        );
        wrapper.orderByAsc("time");
        List<Msg> list = list(wrapper);
        return list;
    }

    @Override
    public long getPersonMsgCountPer(long senderId) {
        long memberId = MemberIdHolder.getMemberId();
        QueryWrapper<Msg> wrapper = new QueryWrapper<>();
        wrapper.ne("sender_id", senderId);
        wrapper.eq("receiver_id", memberId);
        wrapper.eq("sign", 0);
        long count = count(wrapper);
        return count;
    }

    @Override
    public List<Msg> getLatestHead() {
        long receiverId = MemberIdHolder.getMemberId();
        List<Msg> list = msgMapper.getLatestHead(receiverId);
        return list;
    }


}
