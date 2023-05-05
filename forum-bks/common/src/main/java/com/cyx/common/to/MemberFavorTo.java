package com.cyx.common.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel("member查询因子")
public class MemberFavorTo {
    @ApiModelProperty("按照memberId进行查询")
    private Long id;
    @ApiModelProperty("按照用户名进行查询")
    private String username;
    @ApiModelProperty("按照电话号码进行查询")
    private String phone;
}
