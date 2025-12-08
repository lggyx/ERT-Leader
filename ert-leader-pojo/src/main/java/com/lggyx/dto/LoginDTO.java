package com.lggyx.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class LoginDTO {
    @Schema(description = "账号")
    private String account;
    @Schema(description = "密码")
    private String password;
}
