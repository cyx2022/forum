package com.cyx.main.service;

import com.baomidou.mybatisplus.extension.service.IService;

import com.cyx.common.entity.Store;

import java.util.Map;

/**
 * 
 *
 * @author cyx
 * @email 2906665963@qq.com
 * @date 2023-03-24 21:05:26
 */
public interface StoreService extends IService<Store> {

    boolean add(Map<String,Long> map);

    boolean cancel(Map<String,Long> map);

    boolean check(Long contentId);
}

