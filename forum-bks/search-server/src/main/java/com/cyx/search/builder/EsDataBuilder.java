package com.cyx.search.builder;

import com.alibaba.fastjson.TypeReference;
import com.cyx.common.entity.Bar;
import com.cyx.common.entity.Content;
import com.cyx.search.constant.EsDataConstant;
import com.cyx.search.entity.EsData;
import com.cyx.search.feign.MainFeignService;
import com.cyx.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EsDataBuilder implements CommandLineRunner {

    @Autowired
    SearchService searchService;

    @Autowired
    MainFeignService mainFeignService;

    @Override
    public void run(String... args) throws Exception {
        List<Bar> barList = mainFeignService.getBar().getObject(new TypeReference<List<Bar>>() {
        });
        List<Content> contentList = mainFeignService.getContent().getObject(new TypeReference<List<Content>>() {
        });
        List<EsData> esBarList = new ArrayList<>();
        List<EsData> esContentList = new ArrayList<>();
        barList.forEach(e -> {
            EsData esData = new EsData(e.getId(), e.getName(), null, EsDataConstant.BAR);
            esBarList.add(esData);
        });

        contentList.forEach(e -> {
            EsData esData = new EsData(e.getId(), e.getTitle(), e.getText(), EsDataConstant.CONTENT);
            esContentList.add(esData);
        });
        boolean flag = searchService.createIndex(EsDataConstant.INDEX);
        if (flag) {
            searchService.importData(EsDataConstant.INDEX, esBarList);
            searchService.importData(EsDataConstant.INDEX, esContentList);
        }
        System.out.println("数据导入ES完成");
    }
}
