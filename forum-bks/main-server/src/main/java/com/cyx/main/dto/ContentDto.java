package com.cyx.main.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("content的概要返回对象")
public class ContentDto {
    @ApiModelProperty("content id")
    private Long id;

    @ApiModelProperty("评论者id")
    private Long memberId;

    @ApiModelProperty("bar空间id")
    private Long barId;

    @ApiModelProperty("content的类型")
    private Long contentType;

    @ApiModelProperty("bar空间的名称")
    private String barName;


    @ApiModelProperty("content的图片地址")
    private String[] urls;

    private String url;

    @ApiModelProperty("content的标题")
    private String title;

    @ApiModelProperty("content的内容")
    private String text;

    @ApiModelProperty("content的发布时间")
    private Date publishTime;

    @ApiModelProperty("content的更新时间")
    private Date updateTime;

    @ApiModelProperty("被分享次数")
    private Long shareCount;

    @ApiModelProperty("被收藏次数")
    private Long storeCount;

    @ApiModelProperty("被评论次数")
    private Long commentCount;
}
