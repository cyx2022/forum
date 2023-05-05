package com.cyx.main.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//bar的概要返回对象
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("bar返回信息概要")
public class BarDto {
    @ApiModelProperty("bar id")
    private Long id;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("人数")
    private Long number;

    @ApiModelProperty("图片地址")
    private String img;

    @ApiModelProperty("标签名")
    private String labelName;

    @ApiModelProperty("简介")
    private String tip;

    @ApiModelProperty("content总数")
    private Long contentCount;

    //    TODO 要考虑把二级评论的数量加上
    @ApiModelProperty("一级评论总数")
    private Long commentCount;
}
