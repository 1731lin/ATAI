package com.atai.compentition.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class RankingQuery {
    @ApiModelProperty(value = "团队名称")
    private String teamName;

    @ApiModelProperty(value = "分数")
    private Integer score;

    @ApiModelProperty(value = "最优日期提交日")
    private Date deadline;

}
