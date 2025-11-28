package com.lggyx.pojo.vo;

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
        private Long id;
        private String name;
        private String role;
    }
}
