package com.cyx.main.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class SensitiveWord {
    @ExcelProperty("word")
    public String word;
    @ExcelProperty("time")
    public Date time;
}
