package com.lggyx.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

@Data
@Schema(description = "用户信息DTO")
public class UserDTO {

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
}
