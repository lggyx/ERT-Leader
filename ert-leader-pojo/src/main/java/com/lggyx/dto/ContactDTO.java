package com.lggyx.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ContactDTO {
    @Schema(description="公司名称")
    private String company;

    @Schema(description="联系电话")
    private String phone;

    @Schema(description="联系邮箱")
    private String email;

    @Schema(description="办公地址")
    private String address;
}
