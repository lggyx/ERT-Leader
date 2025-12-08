package com.lggyx.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CreateAssessmentDTO {
    @Schema(description = "测评类型：QUICK-极速测评(12题), FULL-完整测评(36题)")
    private String type;
}
