package com.lggyx.controller;


import com.lggyx.pojo.dto.LoginDTO;
import com.lggyx.pojo.dto.UserDTO;
import com.lggyx.pojo.vo.LoginVO;
import com.lggyx.pojo.vo.UserVO;
import com.lggyx.result.Result;
import com.lggyx.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 测评用户档案 前端控制器
 * </p>
 *
 * @author lggyx
 * @since 2025-11-25
 */
@RestController
@Tag(name = "用户管理", description = "测评用户档案")
public class UserController {
    @Resource
    public IUserService userService;

    @PostMapping("/api/auth/register")
    @Operation(description = "用户注册")
    public Result<UserVO> register(@RequestBody UserDTO userDTO) {
        return userService.add(userDTO);
    }

    @PostMapping("/api/auth/login")
    @Operation(description = "用户登录")
    public Result<LoginVO> login(@RequestBody @Valid LoginDTO loginDTO) {
        return userService.login(loginDTO);
    }

}
