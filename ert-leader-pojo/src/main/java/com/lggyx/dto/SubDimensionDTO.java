package com.lggyx.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SubDimensionDTO {
    @Schema(description="子维度名称")
    private String name;

    @Schema(description="子维度说明")
    private String description;
}
