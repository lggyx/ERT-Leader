package com.lggyx.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ResultVO {
    @Schema(description = "测评ID")
    private int assessmentId;
    @Schema(description = "用户ID")
    private String userName;
    @Schema(description = "测评类型")
    private String type;
    @Schema(description = "测评完成时间")
    private Date completedAt;
    @Schema(description = "测评得分")
    private DimensionScores dimensionScores;
    @Schema(description = "测评画像")
    private PortraitVO portrait;
    @Schema(description = "测评子维度得分")
    private List<SubDimensionScores> subDimensionScores;
    @Data
    public static class E {
        @Schema(description = "测评得分")
        private int score;
        @Schema(description = "测评得分等级")
        private String level;
        @Schema(description = "测评得分描述")
        private String description;
    }
    @Data
    public static class R {
        @Schema(description = "测评得分")
        private int score;
        @Schema(description = "测评得分等级")
        private String level;
        @Schema(description = "测评得分描述")
        private String description;
    }
    @Data
    public static class T {
        @Schema(description = "测评得分")
        private int score;
        @Schema(description = "测评得分等级")
        private String level;
        @Schema(description = "测评得分描述")
        private String description;
    }
    @Data
    public static class DimensionScores {
        @Schema(description = "测评得分")
        private E E;
        @Schema(description = "测评得分")
        private R R;
        @Schema(description = "测评得分")
        private T T;
    }
    @Data
    public static class PortraitVO {
        @Schema(description = "测评画像编码")
        private String code;
        @Schema(description = "测评画像名称")
        private String description;
    }
    @Data
    public static class SubDimensionScores  {
        @Schema(description = "测评子维度编码")
        private String code;
        @Schema(description = "测评子维度名称")
        private String name;
        @Schema(description = "测评得分")
        private int score;
        @Schema(description = "测评得分等级")
        private String dimension;
        @Schema(description = "测评得分描述")
        private String actionPlan;
    }
}
