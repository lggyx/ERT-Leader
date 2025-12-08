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
 * 三大能力维度主表
 * </p>
 *
 * @author lggyx
 * @since 2025-11-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("dimension")
@Schema(description="三大能力维度主表")
public class Dimension implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description="维度编码：E执行/R关系/T思考")
    @TableId(value = "code", type = IdType.AUTO)
    private String code;

    @Schema(description="维度名称")
    private String name;

    @Schema(description="维度说明（富文本）")
    private String description;


}
