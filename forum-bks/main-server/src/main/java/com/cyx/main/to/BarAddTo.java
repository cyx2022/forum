package com.cyx.main.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("新增bar的表单数据")
public class BarAddTo {
    @ApiModelProperty("所属的类别标签")
    private Long labelId;
    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("简介")
    private String tip;
    @ApiModelProperty("头像地址")
    private String url;
}
