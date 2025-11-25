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
@TableName("sub_dimension")
public class SubDimension implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 子维度编码
     */
    @TableId(value = "code", type = IdType.AUTO)
    private String code;

    /**
     * 所属维度编码
     */
    private String dimensionCode;

    /**
     * 子维度名称
     */
    private String name;

    /**
     * 子维度描述
     */
    private String description;


}
