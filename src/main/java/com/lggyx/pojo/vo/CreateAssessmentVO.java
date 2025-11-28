package com.lggyx.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
@Data
public class CreateAssessmentVO {
    @Schema(description = "测评记录主键")
    private Long assessmentId;
    @Schema(description = "测评类型：QUICK-极速测评(12题), FULL-完整测评(36题)")
    private String type;
    @Schema(description = "测评状态：INIT-进行中, DONE-已完成")
    private String status;
    @Schema(description = "测评题目数量")
    private Integer questionCount;
}
