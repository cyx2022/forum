package com.cyx.main.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//comment添加参数
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("新增comment的表单参数")
public class CommentAddTo {
    @ApiModelProperty("被评论的内容实体id")
    private Long contentId;
    @ApiModelProperty("评论的文字内容")
    private String content;
    @ApiModelProperty("评论携带的图片")
    private String[] urls;
}
