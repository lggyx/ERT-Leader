package com.lggyx.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UpdateErtScoreDescDTO {
    @Schema(description="区间下限（含）")
    private Integer minScore;

    @Schema(description="区间上限（含）")
    private Integer maxScore;

    @Schema(description="区间文字描述")
    private String description;
}
