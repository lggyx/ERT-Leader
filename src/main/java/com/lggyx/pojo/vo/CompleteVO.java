package com.lggyx.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CompleteVO {
    @Schema(description = "测评记录主键")
    private int assessmentId;
    @Schema(description = "测评状态：INIT-进行中, DONE-已完成")
    private String status;
    @Schema(description = "测评得分")
    private int eScore;
    @Schema(description = "测评得分")
    private int rScore;
    @Schema(description = "测评得分")
    private int tScore;
    @Schema(description = "测评得分")
    private int portraitId;
    @Schema(description = "测评报告")
    private String portraitDesc;
}
