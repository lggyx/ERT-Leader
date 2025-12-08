package com.lggyx.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CreateQuestionDTO {
    @Schema(description = "子维度编码")
    private String subDimCode;
    @Schema(description = "题干")
    private String content;
    @Schema(description = "排序")
    private int seq;
}
