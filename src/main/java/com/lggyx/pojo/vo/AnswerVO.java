package com.lggyx.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AnswerVO {
    @Schema(description = "测评记录ID")
    private int assessmentId;
    @Schema(description = "问题ID")
    private int questionId;
    @Schema(description = "选项ID")
    private boolean answered;
    @Schema(description = "剩余问题数量")
    private int remainQuestions;
}
