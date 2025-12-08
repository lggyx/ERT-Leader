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
 * 12 项子能力配置
 * </p>
 *
 * @author lggyx
 * @since 2025-11-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sub_dimension")
@Schema(description="12 项子能力配置")
public class SubDimension implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description="子维度编码，如 E1")
    @TableId(value = "code", type = IdType.AUTO)
    private String code;

    @Schema(description="所属大维度")
    private String dimensionCode;

    @Schema(description="子维度名称")
    private String name;

    @Schema(description="子维度说明")
    private String description;


}
