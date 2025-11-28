package com.lggyx.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SubDimensionVO {
    @Schema(description="子维度编码，如 E1")
    private String code;
    @Schema(description="所属大维度")
    private String dimensionCode;
    @Schema(description="子维度名称")
    private String name;
    @Schema(description="子维度说明")
    private String description;
}
