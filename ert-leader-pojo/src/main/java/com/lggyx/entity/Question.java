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
 * 测评题库
 * </p>
 *
 * @author lggyx
 * @since 2025-11-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("question")
@Schema(description="测评题库")
public class Question implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description= "题目主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description= "子维度外键")
    private String subDimCode;

    @Schema(description= "题干（富文本）")
    private String content;

    @Schema(description= "同一子维度下的排序号")
    private Integer seq;


}
