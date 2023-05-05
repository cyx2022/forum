package com.cyx.search.service.impl;

import com.alibaba.fastjson.TypeReference;
import com.cyx.common.entity.Bar;
import com.cyx.common.entity.Content;
import com.cyx.search.constant.EsDataConstant;
import com.cyx.search.entity.EsData;
import com.cyx.search.feign.MainFeignService;
import com.cyx.search.service.BarSearchService;
import com.cyx.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class BarSearchServiceImpl implements BarSearchService {
    @Autowired
    SearchService searchService;

    @Autowired
    MainFeignService mainFeignService;
    @Override
    public List<Bar> searchByWord(String word) throws IOException {
        List<String> l1= new ArrayList<>();
        l1.add(EsDataConstant.TYPE);
        List<String> l2=new ArrayList<>();
        l2.add(EsDataConstant.BAR);
        List<EsData> res = searchService.search(EsDataConstant.INDEX, l1, l2,null,null, EsDataConstant.SIZE, EsData.class);
        List<Long> ids=new ArrayList<>();
        res.forEach(e->{
            ids.add(e.getId());
        });
        List<Bar> barList = mainFeignService.fetchBarByIds(ids).getObject(new TypeReference<List<Bar>>() {
        });
        return barList;
    }
}
