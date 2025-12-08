package com.lggyx.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AnswerDTO {
    @Schema(description = "问题ID")
    private int questionId;
    @Schema(description = "选项ID")
    private int optionId;
}
