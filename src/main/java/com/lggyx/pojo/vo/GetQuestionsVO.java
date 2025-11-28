package com.lggyx.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class GetQuestionsVO {
    @Schema(description = "问题ID")
    private int questionId;
    @Schema(description = "子维度代码")
    private String subDimCode;
    @Schema(description = "子维度名称")
    private String subDimName;
    @Schema(description = "问题内容")
    private String content;
    @Schema(description = "问题顺序")
    private int seq;
    @Schema(description = "问题选项")
    private List<Options> options;

    @Data
    public static class Options {
        @Schema(description = "选项ID")
        private int optionId;
        @Schema(description = "选项内容")
        private String label;
        @Schema(description = "选项分数")
        private int score;
        @Schema(description = "选项顺序")
        private int seq;
    }
}
