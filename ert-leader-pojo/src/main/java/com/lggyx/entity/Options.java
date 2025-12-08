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
 * 题目选项
 * </p>
 *
 * @author lggyx
 * @since 2025-12-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("options")
@Schema(name="Options对象", description="题目选项")
public class Options implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(name = "选项主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(name = "所属题目")
    private Long questionId;

    @Schema(name = "选项文本（如'非常符合'）")
    private String label;

    @Schema(name = "分值（1-5）")
    private Integer score;

    @Schema(name = "选项顺序")
    private Integer seq;


}
