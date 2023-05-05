package com.cyx.main.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyx.common.entity.SubComment;
import com.cyx.common.msg.PointMsg;
import com.cyx.common.holder.MemberIdHolder;
import com.cyx.main.mapper.SubCommentMapper;
import com.cyx.main.service.SubCommentService;
import com.cyx.main.service.mq.SenderService;
import com.cyx.main.to.SubCommentAddTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SubCommentServiceImpl extends ServiceImpl<SubCommentMapper, SubComment> implements SubCommentService {
    @Autowired
    SenderService senderService;
    @Override
    public Page<SubComment> getByCommentId(long commentId, Page<SubComment> page) {
        QueryWrapper<SubComment> wrapper=new QueryWrapper<>();
        wrapper.eq("comment_id",commentId);
        Page<SubComment> res = page(page, wrapper);
        return res;
    }

    @Override
    public SubComment addSubComment(SubCommentAddTo to) {
        long fromMemberId = MemberIdHolder.getMemberId();
        Date date=new Date();
        SubComment subComment=new SubComment();
        subComment.setCommentId(to.getCommentId());
        subComment.setFromMemberId(fromMemberId);
        if(to.getTo()==null)
        subComment.setToMemberId(-1L);
        subComment.setContent(to.getContent());
        subComment.setTime(date);
        subComment.setFlag(0);
        boolean res = save(subComment);
        if(res){
            Long id = subComment.getId();
            PointMsg msg = new PointMsg(-1L,id,date, fromMemberId,1L);
            senderService.send(msg);
        }
        return subComment;
    }
}
