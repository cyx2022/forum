package com.cyx.main.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyx.common.entity.Bar;
import com.cyx.main.dto.BarDto;
import com.cyx.main.to.BarAddTo;
import com.cyx.main.to.BarFavorTo;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author cyx
 * @email 2906665963@qq.com
 * @date 2023-03-24 21:05:26
 */
public interface BarService extends IService<Bar> {

    Page<BarDto> getList(BarFavorTo to, Page<BarDto> page);

    BarDto getBar(long id);

    long add(BarAddTo to);

    boolean toUpdate(Long id,Long labelId, String name, String tip,String img);

    List<Bar> feignList();

    Long getByContentId(Long contentId);

    List<Bar> feignByIds(List<Long> ids);

    boolean delete(Map<String, Long> map);
}

