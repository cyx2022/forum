package com.cyx.common.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@TableName("member")
@ApiModel("用户实体")
public class Member {
    @TableId
    @ApiModelProperty("唯一主键")
    private Long id;
    @ApiModelProperty("用户名")
    private String name;
    @ApiModelProperty("密码")
    private String password;
    @ApiModelProperty("性别")
    private int sex;
    @ApiModelProperty("邮箱")
    private String email;
    @ApiModelProperty("电话")
    private String phone;
    @ApiModelProperty("等级")
    private int levelId;
    @ApiModelProperty("头像地址")
    private String avatar;
    @ApiModelProperty("")
    private Date registerTime;
    @ApiModelProperty("上次登录时间")
    private Date lastLogin;
    @ApiModelProperty("Gitee进行第三方登录的令牌")
    private String GiteeToken;
    @ApiModelProperty("微信进行第三方登录的令牌")
    private String WxToken;
    @ApiModelProperty("删除标记")
    private int flag;
}
