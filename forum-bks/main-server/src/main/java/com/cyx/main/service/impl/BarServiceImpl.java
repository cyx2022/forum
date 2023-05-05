package com.cyx.main.service.impl;


import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyx.common.entity.Bar;
import com.cyx.common.entity.Label;
import com.cyx.common.exception.ParamsException;
import com.cyx.common.resp.RespBeanEnum;
import com.cyx.main.constant.ImageConstant;
import com.cyx.main.constant.MQConstant;
import com.cyx.main.constant.RedisTimeOutConstant;
import com.cyx.main.dto.BarDto;
import com.cyx.main.feign.UploadFeignService;
import com.cyx.common.holder.MemberIdHolder;
import com.cyx.main.mapper.BarMapper;
import com.cyx.main.service.LabelService;
import com.cyx.main.service.mq.SenderService;
import com.cyx.main.to.BarAddTo;
import com.cyx.main.to.BarFavorTo;
import com.cyx.main.utils.key.BarCacheKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.cyx.main.service.BarService;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;


@Service
public class BarServiceImpl extends ServiceImpl<BarMapper, Bar> implements BarService {

    @Autowired
    UploadFeignService uploadFeignService;
    @Resource
    BarMapper barMapper;

    @Resource
    RedisTemplate redisTemplate;
    @Autowired
    LabelService labelService;

    @Autowired
    SenderService senderService;


    @Resource
    RedisScript<Boolean> storeImage;

    @Value("${minio.bucketName}")
    String bucketName;
    @Value("${minio.endpoint}")
    String endpoint;
    @Value("${minio.default}")
    String defaultImg;


    @Value("${MQConfig.delayExchange}")
    private String delayExchange;

    @Value("${MQConfig.barDelayRoute}")
    private String barDelayRoute;

    @Override
    public Page<BarDto> getList(BarFavorTo to, Page<BarDto> page) {
        String key = BarCacheKey.getListKey(to, page);
        Page<BarDto> res = (Page<BarDto>) redisTemplate.opsForValue().get(key);
        if (res != null) return res;

        Page<BarDto> list = barMapper.getList(page, to);
        list.getRecords().forEach(e -> {
            if (e.getCommentCount() == null) {
                e.setCommentCount(0L);
            }
            if (e.getContentCount() == null) {
                e.setContentCount(0L);
            }
            if (e.getNumber() == null) {
                e.setNumber(0L);
            }
        });
        redisTemplate.opsForValue().set(key, list, RedisTimeOutConstant.BAR_LIST_TIMEOUT, TimeUnit.SECONDS);
        return list;
    }

    @Override
    public BarDto getBar(long id) {
        String key = BarCacheKey.getSingleKey(id);
        BarDto res = (BarDto) redisTemplate.opsForValue().get(key);
        if (res != null) return res;

        res = barMapper.getBar(id);
        redisTemplate.opsForValue().set(key, res, RedisTimeOutConstant.BAR_SINGLE_TIMEOUT, TimeUnit.SECONDS);
        return res;
    }

    @Override
    public long add(BarAddTo to) {
        int start = to.getUrl().indexOf(bucketName) + bucketName.length() + 1;
        String key = ImageConstant.IMAGE_CACHE_HEAD + to.getUrl().substring(start);
        boolean flag = holdImage(key);
        if (!flag) {
            to.setUrl(endpoint + "/" + bucketName + "/" + defaultImg);

        }
        long memberId = MemberIdHolder.getMemberId();
        Bar bar = new Bar();
        bar.setLabelId(to.getLabelId());
        bar.setBuilderId(memberId);
        bar.setBuildDate(new Date());
        bar.setNumber(0L);
        bar.setFlag(0);
        bar.setName(to.getName());
        bar.setImg(to.getUrl());
        bar.setTip(to.getTip());
        boolean res = save(bar);
//        创建缓存
        if (res) {
            String key2 = BarCacheKey.getSingleKey(bar.getId());
            redisTemplate.opsForValue().set(key2, res, RedisTimeOutConstant.BAR_SINGLE_TIMEOUT, TimeUnit.SECONDS);
            return bar.getId();
        } else {
            return -1;
        }
    }

    @Override
    public boolean toUpdate(Long id, Long labelId, String name, String tip, String img) {
//      检查图片是否在服务器,不存在就设置默认
        boolean b = holdImage(img);
        if (!b) {
            int start = img.indexOf(bucketName);
            String path = img.substring(start);
            Boolean exist = uploadFeignService.exist(bucketName, path).getObject(new TypeReference<Boolean>() {
            });
            if (!exist) {
                img = (endpoint + "/" + bucketName + "/" + defaultImg);
            }
        }

        Bar bar = getById(id);
        List<Label> list = labelService.getList();
        boolean flag = false;
        for (Label label : list) {
            if (label.getId() == labelId) {
                flag = true;
                break;
            }
        }
        if (!flag)
            throw new ParamsException(RespBeanEnum.BIND_ERROR);
        bar.setLabelId(labelId);
        bar.setName(name);
        bar.setTip(tip);
        bar.setImg(img);
        boolean res = updateById(bar);
        if (res) {
//            更新缓存
            String key = BarCacheKey.getSingleKey(id);
            redisTemplate.opsForValue().set(key, res, RedisTimeOutConstant.BAR_SINGLE_TIMEOUT, TimeUnit.SECONDS);
        }
        return res;
    }

    @Override
    public List<Bar> feignList() {
        QueryWrapper<Bar> wrapper = new QueryWrapper<>();
        wrapper.select("id", "name");
        List<Bar> res = this.list(wrapper);
        return res;
    }

    @Override
    public Long getByContentId(Long contentId) {
        Long id = barMapper.getByContentId(contentId);
        return id;
    }

    @Override
    public List<Bar> feignByIds(List<Long> ids) {
        QueryWrapper<Bar> wrapper = new QueryWrapper<>();
        wrapper.in("id", ids);
        List<Bar> list = list(wrapper);
        return list;
    }

    @Override
    public boolean delete(Map<String, Long> map) {
        Long barId = map.get("id");
        if (barId == null) return false;
        Bar bar = getById(barId);
        if (bar!=null&&bar.getBuilderId() == MemberIdHolder.getMemberId()) {
//            boolean res = removeById(barId);
            bar.setFlag(1);
            boolean res = updateById(bar);
            if(res) {
                senderService.sendDelay(delayExchange, barDelayRoute, BarCacheKey.getSingleKey(bar.getId()), MQConstant.DELETE_DELAY);
                return true;
            }
            else
            return false;
        } else {
            return false;
        }
    }

    //    查看图片是否过期删除
    public boolean holdImage(String key) {
        List<String> list = new ArrayList<>();
        list.add(key);
        boolean res = (Boolean) redisTemplate.execute(storeImage, list, Collections.EMPTY_LIST);
        return res;
    }
}