package com.cyx.main.to;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("content的查询因子")
public class ContentFavorTo {
    @ApiModelProperty("创建者id")
    private Long memberId;
    @ApiModelProperty("所属的bar空间")
    private Long barId;
    @ApiModelProperty("排序方式: 发布时间 社交属性")
    private Integer order;
}
