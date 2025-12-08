package com.lggyx.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UpdateSubScoreActionDTO {
    @Schema(description="区间下限")
    private Integer minScore;

    @Schema(description="区间上限")
    private Integer maxScore;

    @Schema(description="可执行的具体行动方案（富文本）")
    private String actionPlan;
}
