package com.lggyx.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 测评用户档案
 * </p>
 *
 * @author lggyx
 * @since 2025-11-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user")
@Schema(description="测评用户档案")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description="用户主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description="手机号（唯一）")
    private String mobile;

    @Schema(description="邮箱（唯一）")
    private String email;

    @Schema(description="密码（bcrypt 加密）")
    private String password;

    @Schema(description="真实姓名")
    private String name;

    @Schema(description="性别：0 未知，1 男，2 女")
    private Integer gender;

    @Schema(description="出生日期")
    private LocalDate birthDate;

    @Schema(description="账号状态：0 禁用，1 启用")
    private Integer status;

    @Schema(description="角色：USER/INST_ADMIN/SUPER_ADMIN")
    private String role;

    @Schema(description="注册时间")
    private LocalDateTime createdAt;

    @Schema(description="最后更新时间")
    private LocalDateTime updatedAt;


}
