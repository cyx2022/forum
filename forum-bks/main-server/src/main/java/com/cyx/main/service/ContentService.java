package com.cyx.main.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyx.common.entity.Bar;
import com.cyx.common.entity.Content;
import com.cyx.main.dto.ContentDto;
import com.cyx.main.to.ContentAddTo;
import com.cyx.main.to.ContentFavorTo;
import com.cyx.main.to.ContentUpdateTo;

import java.util.List;

/**
 * 
 *
 * @author cyx
 * @email 2906665963@qq.com
 * @date 2023-03-24 21:05:26
 */
public interface ContentService extends IService<Content> {

    Page<ContentDto> getList(ContentFavorTo to, Page<Content> page);

    long getCount(ContentFavorTo to);

    Page<ContentDto> getByLabelId(long label, long order, Page<Content> page);

    boolean addContent(ContentAddTo to);


    Content getOneById(long id);

    boolean updateContent(ContentUpdateTo to);

    List<Content> feignList();

    List<Content> feignByIds(List<Long> ids);

    Page<ContentDto> recommend(Long barId, Page<Content> page);

    Page<ContentDto> getByOwner(Long id, Page<Content> page);

    boolean delById(Long contentId);

}

