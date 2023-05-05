package com.cyx.main.to;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("content的修改参数列表")
public class ContentUpdateTo {
    private Long contentId;
    private String title;
    private String text;
    private String[] urls;



}
