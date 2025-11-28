package com.lggyx.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class DimensionVO {
    @Schema(description = "维度编码")
    private String code;
    @Schema(description = "维度名称")
    private String name;
    @Schema(description = "维度描述")
    private String description;
    @Schema(description = "子维度数量")
    private int subDimensionCount;
}
