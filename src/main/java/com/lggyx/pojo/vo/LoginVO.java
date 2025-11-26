package com.lggyx.pojo.vo;

import lombok.Data;

@Data
public class LoginVO {
    private String token;
    private String expiresIn;
    private UserInfo userInfo;
    @Data
    public static class UserInfo{
        private Long id;
        private String name;
        private String role;
    }
}
