package com.lggyx.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema
@Data
public class ErtScoreDescVO {
    @Schema(description="主键")
    private Integer id;

    @Schema(description="维度 E/R/T")
    private String dimensionCode;

    @Schema(description="区间下限（含）")
    private Integer minScore;

    @Schema(description="区间上限（含）")
    private Integer maxScore;

    @Schema(description="区间文字描述")
    private String description;
}
