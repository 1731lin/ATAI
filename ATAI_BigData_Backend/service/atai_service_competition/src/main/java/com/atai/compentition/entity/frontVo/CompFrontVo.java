package com.atai.compentition.entity.frontVo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CompFrontVo {
    @ApiModelProperty(value = "比赛名称")
    private String name;

    @ApiModelProperty(value = "比赛类型")
    private String level;

    @ApiModelProperty(value = "热度排序")
    private String hotSort;

    @ApiModelProperty(value = "最新时间排序")
    private String gmtCreateSort;

    @ApiModelProperty(value = "奖金排序")
    private String priceSort;
}
