package com.cyx.common.msg;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("由首页积分赠送新增积分消息实体")
public class PointWelfareMsg {
    @ApiModelProperty("被赠送者id")
    private long MemberId;
    @ApiModelProperty("新增积分总数")
    private long point;
}
