package com.cyx.main.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//subComment的查询因子
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("新增二级评论的表单参数")
public class SubCommentAddTo {
    @ApiModelProperty("二级评论对谁进行评论")
    public Long to;
    @ApiModelProperty("一级评论id")
    private Long commentId;
    @ApiModelProperty("评论内容")
    private String content;
}
