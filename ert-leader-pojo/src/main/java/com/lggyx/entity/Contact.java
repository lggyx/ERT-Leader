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
 * 系统联系信息（仅一行）
 * </p>
 *
 * @author lggyx
 * @since 2025-11-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("contact")
@Schema(description="系统联系信息（仅一行）")
public class Contact implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description="固定主键=1，全局唯一")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description="公司名称")
    private String company;

    @Schema(description="联系电话")
    private String phone;

    @Schema(description="联系邮箱")
    private String email;

    @Schema(description="办公地址")
    private String address;


}
