package com.lggyx.pojo.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserVO {
    @Schema(description="用户主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description="手机号（唯一）")
    private String mobile;

    @Schema(description="邮箱（唯一）")
    private String email;

    @Schema(description="真实姓名")
    private String name;

    @Schema(description="角色：USER/INST_ADMIN/SUPER_ADMIN")
    private String role;
}
