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
public class Portrait implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * E 维度等级：H 高，L 低
     */
    private String eLevel;

    /**
     * R 维度等级：H 高，L 低
     */
    private String rLevel;

    /**
     * T 维度等级：H 高，L 低
     */
    private String tLevel;

    /**
     * 画像描述
     */
    private String description;


}
