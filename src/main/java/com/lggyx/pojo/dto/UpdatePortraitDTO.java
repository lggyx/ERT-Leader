package com.lggyx.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UpdatePortraitDTO {
    @Schema(description = "画像描述（富文本）")
    private String description;
}
