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
 * 27 种领导力画像
 * </p>
 *
 * @author lggyx
 * @since 2025-11-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("portrait")
@Schema(description="27 种领导力画像")
public class Portrait implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description= "画像主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description= "E 维度等级：H 高，L 低")
    private String eLevel;

    @Schema(description= "R 维度等级：H 高，L 低")
    private String rLevel;

    @Schema(description= "T 维度等级：H 高，L 低")
    private String tLevel;

    @Schema(description= "画像描述（富文本）")
    private String description;


}
