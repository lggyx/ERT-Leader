package com.lggyx.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
public class GetHistoryVO {
    @Schema(description = "测评记录ID")
    private int assessmentId;
    @Schema(description = "测评类型")
    private String type;
    @Schema(description = "测评状态")
    private String status;
    @Schema(description = "测评完成时间")
    private Date completedAt;
    @Schema(description = "E得分")
    private int eScore;
    @Schema(description = "R得分")
    private int rScore;
    @Schema(description = "T得分")
    private int tScore;
    @Schema(description = "画像代码")
    private String portraitCode;
}
