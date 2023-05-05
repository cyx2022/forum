package com.cyx.main.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyx.common.entity.Content;
import com.cyx.main.constant.ContentOrderContant;
import com.cyx.main.constant.MQConstant;
import com.cyx.main.constant.RedisTimeOutConstant;
import com.cyx.main.dto.ContentDto;
import com.cyx.common.holder.MemberIdHolder;
import com.cyx.main.service.mq.SenderService;
import com.cyx.main.to.ContentAddTo;
import com.cyx.main.to.ContentFavorTo;
import com.cyx.main.to.ContentUpdateTo;
import com.cyx.main.utils.key.ContentCacheKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.cyx.main.mapper.ContentMapper;
import com.cyx.main.service.ContentService;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


@Service("contentService")
public class ContentServiceImpl extends ServiceImpl<ContentMapper, Content> implements ContentService {

    @Autowired
    ContentService contentService;

    @Resource
    ContentMapper contentMapper;

    @Resource
    RedisTemplate redisTemplate;

    @Autowired
    SenderService senderService;

    @Value("${MQConfig.delayExchange}")
    private String delayExchange;

    @Value("${MQConfig.contentDelayRoute}")
    private String contentDelayRoute;

    @Override
    public Page<ContentDto> getList(ContentFavorTo to, Page<Content> page) {
        String key=ContentCacheKey.getListToKey(to,page);
        Page<ContentDto> res;
         res= (Page<ContentDto>)redisTemplate.opsForValue().get(key);
        if(res!=null)
            return res;
        if(to.getOrder()==null){
            to.setOrder(1);
        }
        res=contentMapper.getList(to,page);
        //        对图片的url进行处理
        res.getRecords().forEach(e->{
            if(e.getContentType()>1){
                String s = e.getUrl();
                e.setUrls(s.split("^"));
            }
        });
        redisTemplate.opsForValue().set(key,res,RedisTimeOutConstant.CONTENT_LIST_TIMEOUT,TimeUnit.SECONDS);
        return res;
    }

    @Override
    public long getCount(ContentFavorTo to) {
        QueryWrapper<Content> wrapper=new QueryWrapper();
        wrapper.eq(to.getBarId()!=null,"bar_id",to.getBarId())
                .eq(to.getMemberId()!=null,"member_id",to.getMemberId());
        long res = contentService.count(wrapper);
        return res;
    }

    @Override
    public Page<ContentDto> getByLabelId(long label, long order, Page<Content> page) {

        String key=ContentCacheKey.getListLabelKey(label,order,page);
        Page<ContentDto>  res;
        res=(Page<ContentDto>)redisTemplate.opsForValue().get(key);
        if(res!=null) return res;

//        需要判断参数异常
        String s = ContentOrderContant.orders.get(label);
        if(s==null){
            s=ContentOrderContant.orders.get(1L);
        }
        res = contentMapper.getByLabelId(label, s,page);
        redisTemplate.opsForValue().set(key,res,RedisTimeOutConstant.CONTENT_LIST_TIMEOUT,TimeUnit.SECONDS);
        return res;
    }

    @Override
    public boolean addContent(ContentAddTo to) {
        Content content=new Content();
        long memberId = MemberIdHolder.getMemberId();
        content.setMemberId(memberId);
        content.setBarId(to.getBarId());
//      TODO  媒体类型
        if(to.getUrls().length==0){
            content.setContentType(0L);
        }else{
            content.setContentType(2L);
        }
        content.setUrl(Arrays.stream(to.getUrls()).collect(Collectors.joining("^")));
        content.setText(to.getTitle());
        content.setText(to.getText());
        content.setPublishTime(new Date());
        content.setUpdateTime(new Date());
        content.setShareCount(0L);
        content.setCommentCount(0L);
        content.setFlag(0);
        boolean res = this.save(content);
        if(res){
            String key = ContentCacheKey.getSingleKey(to.getBarId());
            redisTemplate.opsForValue().set(key,content,RedisTimeOutConstant.CONTENT_SINGLE_TIMEOUT,TimeUnit.SECONDS);
        }
        return res;

    }


    @Override
    public boolean updateContent(ContentUpdateTo to) {
        Content content = this.getById(to.getContentId());
        if(to.getTitle()!=null){
            content.setTitle(to.getTitle());
        }
        content.setText(to.getText());
        if(to.getUrls()!=null) {
            content.setUrl(Arrays.stream(to.getUrls()).collect(Collectors.joining("^")));
            if (to.getUrls().length == 0) {
                content.setContentType(0L);
            } else {
                content.setContentType(1L);
            }
        }
        content.setUpdateTime(new Date());
        QueryWrapper<Content> wrapper=new QueryWrapper<>();
        wrapper.eq("id",to.getContentId());
        boolean res = update(content, wrapper);
        if(res){
            String key = ContentCacheKey.getSingleKey(to.getContentId());
            redisTemplate.opsForValue().set(key,content,RedisTimeOutConstant.CONTENT_SINGLE_TIMEOUT,TimeUnit.SECONDS);
        }
        return res;
    }

    @Override
    public List<Content> feignList() {
        QueryWrapper<Content> wrapper=new QueryWrapper<>();
        wrapper.select("id","title","text");
        List<Content> list = list(wrapper);
        return list;
    }

    @Override
    public List<Content> feignByIds(List<Long> ids) {
        QueryWrapper<Content> wrapper=new QueryWrapper<>();
        wrapper.in("id",ids);
        List<Content> list = list(wrapper);
        return list;
    }



    @Override
    public Content getOneById(long id) {
        String key = ContentCacheKey.getSingleKey(id);
        Content res;
        res = (Content)redisTemplate.opsForValue().get(key);
        if(res!=null) return res;
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("id",id);
        res = contentService.getById(id);
        redisTemplate.opsForValue().set(key,res, RedisTimeOutConstant.CONTENT_SINGLE_TIMEOUT, TimeUnit.SECONDS);

        return res;
    }

    @Override
    public Page<ContentDto> recommend(Long barId, Page<Content> page) {
        Page<ContentDto> res=contentMapper.recommend(barId,page);
        return res;
    }


    @Override
    public Page<ContentDto> getByOwner(Long id, Page<Content> page) {
        Page<ContentDto> res=contentMapper.getByOwner(id,page);
        return res;
    }

    @Override
    public boolean delById(Long contentId) {
        QueryWrapper<Content> wrapper=new QueryWrapper<>();
        wrapper.eq("id",contentId);
        Content one = getOne(wrapper);
        if(one==null){
            return false;
        }else{
            one.setFlag(1);
            boolean flag = updateById(one);
            if(flag) {
                String key = ContentCacheKey.getSingleKey(contentId);
                redisTemplate.delete(key);
                senderService.sendDelay(delayExchange,contentDelayRoute,key, MQConstant.DELETE_DELAY);
                return flag;
            }
            return flag;
        }
    }
}