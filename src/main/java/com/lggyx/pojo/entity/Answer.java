package com.lggyx.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author lggyx
 * @since 2025-11-25
 */
@Getter
@Setter
@Accessors(chain = true)

public class Answer implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 测评记录ID
     */
    @TableId(value = "assessment_id", type = IdType.AUTO)
    private Long assessmentId;

    /**
     * 题目ID
     */
    @TableId(value = "question_id", type = IdType.AUTO)
    private Long questionId;

    /**
     * 选项ID
     */
    private Long optionId;


}
