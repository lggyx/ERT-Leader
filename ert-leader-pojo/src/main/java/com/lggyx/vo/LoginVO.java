package com.lggyx.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class LoginVO {
    @Schema(description = "JWT token")
    private String token;
    @Schema(description = "token过期时间")
    private String expiresIn;
    @Schema(description = "用户信息")
    private UserInfo userInfo;
    @Data
    public static class UserInfo{
        @Schema(description = "用户ID")
        private Long id;
        @Schema(description = "用户名")
        private String name;
        @Schema(description = "用户角色")
        private String role;
    }
}
