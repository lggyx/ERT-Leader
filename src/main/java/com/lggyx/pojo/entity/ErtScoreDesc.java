package com.lggyx.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("ert_score_desc")
public class ErtScoreDesc implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 维度编码：E/R/T
     */
    private String dimensionCode;

    /**
     * 区间最小值
     */
    private Integer minScore;

    /**
     * 区间最大值
     */
    private Integer maxScore;

    /**
     * 区间描述
     */
    private String description;


}
