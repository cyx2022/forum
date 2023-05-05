package com.cyx.main.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyx.common.entity.Inspect;

import java.util.Map;

public interface InspectService extends IService<Inspect> {
    boolean add(Inspect inspect);

    Page<Inspect> listAll(Long barId, Page<Inspect> page);

    Inspect getByContentId(Long type,Long contentId);

    boolean contentAccept(Map<String, Long> map);

    boolean contentRefuse(Map<String, Long> map);

    boolean commentRefuse(Map<String, Long> map);

    boolean commentAccept(Map<String, Long> map);
}
