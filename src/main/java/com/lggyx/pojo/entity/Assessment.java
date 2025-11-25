package com.lggyx.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;
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
public class Assessment implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 测评类型：QUICK 快速，FULL 完整
     */
    private String type;

    /**
     * 状态：INIT 初始化，DONE 已完成
     */
    private String status;

    /**
     * E 维度得分
     */
    private Integer eScore;

    /**
     * R 维度得分
     */
    private Integer rScore;

    /**
     * T 维度得分
     */
    private Integer tScore;

    /**
     * 对应画像ID
     */
    private Integer portraitId;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;


}
