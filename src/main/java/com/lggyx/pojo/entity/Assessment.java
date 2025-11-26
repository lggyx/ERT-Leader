package com.lggyx.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户测评记录
 * </p>
 *
 * @author lggyx
 * @since 2025-11-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("assessment")
@Schema(description="用户测评记录")
public class Assessment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description= "测评记录主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description= "用户外键")
    private Long userId;

    @Schema(description= "测评类型：QUICK 极速，FULL 完整")
    private String type;

    @Schema(description= "测评状态：INIT 进行中，DONE 已完成")
    private String status;

    @Schema(description= "E 维度得分")
    private Integer eScore;

    @Schema(description= "R 维度得分")
    private Integer rScore;

    @Schema(description= "T 维度得分")
    private Integer tScore;

    @Schema(description= "27 画像外键")
    private Integer portraitId;

    @Schema(description= "创建时间")
    private LocalDateTime createdAt;

    @Schema(description= "更新时间")
    private LocalDateTime updatedAt;


}
