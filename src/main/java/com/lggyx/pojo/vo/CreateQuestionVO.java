package com.lggyx.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CreateQuestionVO {
    @Schema(description = "问题ID")
    private Long questionId;
}
