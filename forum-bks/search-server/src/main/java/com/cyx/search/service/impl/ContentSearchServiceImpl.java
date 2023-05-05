package com.cyx.search.service.impl;

import com.alibaba.fastjson.TypeReference;
import com.cyx.common.entity.Content;
import com.cyx.search.constant.EsDataConstant;
import com.cyx.search.entity.EsData;
import com.cyx.search.feign.MainFeignService;
import com.cyx.search.service.ContentSearchService;
import com.cyx.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ContentSearchServiceImpl implements ContentSearchService {
    @Autowired
    SearchService searchService;

    @Autowired
    MainFeignService mainFeignService;

    @Override
    public List<Content> searchByWord(String word) throws IOException {
//        l1、l2是must的
        List<String> l1= new ArrayList<>();
        l1.add(EsDataConstant.TYPE);
        List<String> l2=new ArrayList<>();
        l2.add(EsDataConstant.CONTENT);
//        l3、l4是should的
        List<String> l3=new ArrayList<>();
        l3.add(EsDataConstant.TITLE);
        l3.add(EsDataConstant.TEXT);
        List<String> l4=new ArrayList<>();
        l4.add(word);
        l4.add(word);
        List<EsData> res = searchService.search(EsDataConstant.INDEX, l1, l2,l3,l4,EsDataConstant.SIZE, EsData.class);
        List<Long> ids=new ArrayList<>();
        res.forEach(e->{
            ids.add(e.getId());
        });
        List<Content> contentList = mainFeignService.fetchContentByIds(ids).getObject(new TypeReference<List<Content>>() {
        });
        return contentList;
    }

    @Override
    public List<Content> searchByIdAndWord(String id, String word) throws IOException {
        List<String> l1= new ArrayList<>();
        l1.add(EsDataConstant.TYPE);
        l1.add("id");
        List<String> l2=new ArrayList<>();
        l2.add(EsDataConstant.CONTENT);
        l2.add(id);

        List<String> l3=new ArrayList<>();
        l3.add(EsDataConstant.TITLE);
        l3.add(EsDataConstant.TEXT);
        List<String> l4=new ArrayList<>();
        l4.add(word);
        l4.add(word);
        List<EsData> res = searchService.search(EsDataConstant.INDEX, l1, l2,l3,l4,EsDataConstant.SIZE, EsData.class);
        List<Long> ids=new ArrayList<>();
        res.forEach(e->{
            ids.add(e.getId());
        });
        List<Content> contentList = mainFeignService.fetchContentByIds(ids).getObject(new TypeReference<List<Content>>() {
        });
        return contentList;
    }
}
