package com.lggyx.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 维度得分区间解释
 * </p>
 *
 * @author lggyx
 * @since 2025-11-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ert_score_desc")
@Schema(description="维度得分区间解释")
public class ErtScoreDesc implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description="主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description="维度 E/R/T")
    private String dimensionCode;

    @Schema(description="区间下限（含）")
    private Integer minScore;

    @Schema(description="区间上限（含）")
    private Integer maxScore;

    @Schema(description="区间文字描述")
    private String description;


}
