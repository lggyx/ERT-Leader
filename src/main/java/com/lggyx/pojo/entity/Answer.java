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
 * 用户答题明细
 * </p>
 *
 * @author lggyx
 * @since 2025-11-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("answer")
@Schema(description="用户答题明细")
public class Answer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description= "测评记录外键")
    @TableId(value= "assessment_id", type = IdType.AUTO)
    private Long assessmentId;

    @Schema(description= "题目外键")
    private Long questionId;

    @Schema(description= "选项外键")
    private Long optionId;


}
