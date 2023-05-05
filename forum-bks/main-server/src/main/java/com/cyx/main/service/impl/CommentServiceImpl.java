package com.cyx.main.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyx.common.entity.Comment;
import com.cyx.main.constant.RedisTimeOutConstant;
import com.cyx.main.constant.SeparatorConstant;
import com.cyx.common.holder.MemberIdHolder;
import com.cyx.main.mapper.CommentMapper;
import com.cyx.common.msg.PointMsg;
import com.cyx.main.service.mq.SenderService;
import com.cyx.main.to.CommentAddTo;
import com.cyx.main.utils.key.CommentCacheKey;
import com.cyx.main.utils.StringArrayToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.cyx.main.service.CommentService;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.TimeUnit;


@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {


    @Resource
    RedisTemplate redisTemplate;
    @Autowired
    SenderService senderService;
    @Override
    public Page<Comment> getByContentId(long contentId, Page<Comment> page) {
        String key = CommentCacheKey.getListKey(contentId, page);
        Page<Comment> res;
        res=(Page<Comment>)redisTemplate.opsForValue().get(key);
        if(res!=null) return res;
        res=(Page<Comment>)redisTemplate.opsForValue().get(key);
        if(res!=null) return res;
        QueryWrapper<Comment> wrapper=new QueryWrapper<>();
        wrapper.eq("content_id",contentId);
        res = page(page, wrapper);
        redisTemplate.opsForValue().set(key,res,RedisTimeOutConstant.COMMENT_LIST_TIMEOUT, TimeUnit.SECONDS);
        return res;
    }

    @Override
    public Comment addComment(CommentAddTo to) {
        long memberId = MemberIdHolder.getMemberId();
        Date date=new Date();
        Comment comment=new Comment();
        comment.setContentId(to.getContentId());
        comment.setMemberId(memberId);
        comment.setContent(to.getContent());
        comment.setUrls(StringArrayToString.transform(to.getUrls(), SeparatorConstant.COMMENT_PHOTO_SEPATATOR));
        comment.setTime(date);
        comment.setFlag(0);
        boolean res = save(comment);

        if(res==true) {
//        发表评论成功，为content增加积分 异步
            Long id = comment.getId();
            PointMsg msg = new PointMsg(id,-1L,date, memberId,1L);
            senderService.send(msg);
        }
        return comment;
    }
}