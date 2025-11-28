package com.lggyx.pojo.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CurrentUserVO {
    @Schema(description = "用户主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "手机号（唯一）")
    private String mobile;

    @Schema(description = "邮箱（唯一）")
    private String email;

    @Schema(description = "真实姓名")
    private String name;

    @Schema(description = "性别：0 未知，1 男，2 女")
    private Integer gender;

    @Schema(description = "出生日期")
    private LocalDate birthDate;

    @Schema(description = "账号状态：0 禁用，1 启用")
    private Integer status;

    @Schema(description = "角色：USER/INST_ADMIN/SUPER_ADMIN")
    private String role;
}
