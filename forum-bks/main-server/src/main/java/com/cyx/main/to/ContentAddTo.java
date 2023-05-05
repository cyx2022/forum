package com.cyx.main.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

//新增content
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("新增content的表单参数")
public class ContentAddTo {
    @ApiModelProperty("content所在的bar空间")
    private Long barId;
    @ApiModelProperty("content的标题")
    private String title;
    @ApiModelProperty("content的文字内容")
    private String text;
    @ApiModelProperty("content的图片地址连接字符串")
    private String[] urls;
}
