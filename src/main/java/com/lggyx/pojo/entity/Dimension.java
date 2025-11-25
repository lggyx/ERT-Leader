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
public class Dimension implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 维度编码：E/R/T
     */
    @TableId(value = "code", type = IdType.AUTO)
    private String code;

    /**
     * 维度名称
     */
    private String name;

    /**
     * 维度描述
     */
    private String description;


}
