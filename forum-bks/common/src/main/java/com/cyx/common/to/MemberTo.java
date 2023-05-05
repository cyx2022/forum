package com.cyx.common.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel("登录注册member传输对象")
public class MemberTo {
    @ApiModelProperty("表单用户名")
    private String username;
    @ApiModelProperty("表单密码")
    private String password;
    @ApiModelProperty("表单验证码")
    private String code;
}
