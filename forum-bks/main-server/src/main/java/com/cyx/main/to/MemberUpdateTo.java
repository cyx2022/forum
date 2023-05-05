package com.cyx.main.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//可以更新的member信息
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("进行member信息更新的表单参数")
public class MemberUpdateTo {
    @ApiModelProperty("新用户名")
    private String newName;
    @ApiModelProperty("旧用户名")
    private String oldName;
    @ApiModelProperty("旧密码")
    private String oldPassword;
    @ApiModelProperty("新密码")
    private String newPassword;
    @ApiModelProperty("性别")
    private Integer sex;
    @ApiModelProperty("电话号码")
    private String phone;
    @ApiModelProperty("邮箱地址")
    private String email;
    @ApiModelProperty("头像地址")
    private String avatar;
}
