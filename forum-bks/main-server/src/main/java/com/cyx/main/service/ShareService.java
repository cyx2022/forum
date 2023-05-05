package com.cyx.main.service;

import com.baomidou.mybatisplus.extension.service.IService;

import com.cyx.common.entity.Share;

import java.util.Map;

/**
 * 
 *
 * @author cyx
 * @email 2906665963@qq.com
 * @date 2023-03-24 21:05:26
 */
public interface ShareService extends IService<Share> {

    boolean add(Map<String,Long> map);
}

