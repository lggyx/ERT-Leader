package com.lggyx.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class DimensionDTO {
    @Schema(description="维度名称")
    private String name;

    @Schema(description="维度说明（富文本）")
    private String description;
}
