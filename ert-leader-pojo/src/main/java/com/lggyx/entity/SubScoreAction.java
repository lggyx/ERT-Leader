package com.lggyx.pojo.entity;

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
 * 子维度得分行动方案
 * </p>
 *
 * @author lggyx
 * @since 2025-11-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sub_score_action")
@Schema(description="子维度得分行动方案")
public class SubScoreAction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description="主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description="子维度编码")
    private String subDimCode;

    @Schema(description="区间下限")
    private Integer minScore;

    @Schema(description="区间上限")
    private Integer maxScore;

    @Schema(description="可执行的具体行动方案（富文本）")
    private String actionPlan;


}
