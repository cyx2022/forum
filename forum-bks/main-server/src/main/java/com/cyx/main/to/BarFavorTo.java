package com.cyx.main.to;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;


//多个bar的查询因子
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("bar的查询因子")
public class BarFavorTo {
    @ApiModelProperty("所属的标签id")
    private Long labelId;
    @ApiModelProperty("创建者id")
    private Long builderId;
    @ApiModelProperty("排序方式--默认按照member的数量进行排序")
    private Integer order;
}
