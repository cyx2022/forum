package com.cyx.main.builder;

import com.cyx.main.data.SensitiveData;
import com.cyx.main.entity.SensitiveWord;
import com.cyx.main.utils.ExcelUtil;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

//生成敏感词前缀树
@Component
public class SensitiveWordsLoader implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        List<SensitiveWord> sensitiveWords = ExcelUtil.loadData("E://sensitiveWords.xlsx");
        sensitiveWords.forEach(e->{
            SensitiveData.addWord(e.getWord());
        });
    }



}
