package com.cyx.main.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.cyx.main.entity.SensitiveWord;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExcelUtil {
    public static List<SensitiveWord> loadData(String file){
        List<SensitiveWord> res=new ArrayList<>();
        EasyExcel.read(file, SensitiveWord.class, new AnalysisEventListener<SensitiveWord>() {
            @Override
            public void invoke(SensitiveWord word, AnalysisContext analysisContext) {
                System.out.println(word);
                res.add(word);
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                System.out.println("解析完成");
            }
        }).doReadAll();
        return res;
    }
    public static void writeData(String file){
        SensitiveWord sensitiveWord=new SensitiveWord();
        sensitiveWord.setWord("ddd");
        sensitiveWord.setTime(new Date());
        List<SensitiveWord> list=new ArrayList<>();
        list.add(sensitiveWord);
        EasyExcel.write(file,SensitiveWord.class).sheet("11").doWrite(list);
    }

}
