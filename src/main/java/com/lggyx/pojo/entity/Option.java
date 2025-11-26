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
 * 题目选项
 * </p>
 *
 * @author lggyx
 * @since 2025-11-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("option")
@Schema(description="题目选项")
public class Option implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description= "选项主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description= "所属题目")
    private Long questionId;

    @Schema(description= "选项文本（如'非常符合'）")
    private String label;

    @Schema(description= "分值（1-5）")
    private Integer score;

    @Schema(description= "选项顺序")
    private Integer seq;


}
