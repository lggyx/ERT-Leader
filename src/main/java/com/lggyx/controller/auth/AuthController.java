package com.lggyx.controller.auth;


import com.lggyx.pojo.dto.LoginDTO;
import com.lggyx.pojo.dto.UserDTO;
import com.lggyx.pojo.vo.CurrentUserVO;
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
@Tag(name = "认证与用户模块", description = "测评用户档案")
public class AuthController {
    @Resource
    public IUserService userService;

    @PostMapping("/api/auth/register")
    @Operation(summary = "用户注册", description = "新用户注册，密码需满足8位以上含字母数字")
    public Result<UserVO> register(@RequestBody UserDTO userDTO) {
        return userService.add(userDTO);
    }

    @PostMapping("/api/auth/login")
    @Operation(summary = "用户登录", description = "登录成功后返回JWT token，有效期待定")
    public Result<LoginVO> login(@RequestBody @Valid LoginDTO loginDTO) {
        return userService.login(loginDTO);
    }

    @GetMapping("/api/auth/current")
    @Operation(summary = "获取当前用户信息")
    public Result<CurrentUserVO> getCurrentUser(@RequestHeader("Authorization") String token) {
        // 从请求头中获取token todo 除此之外也可以BaseContext.getCurrentAccount()获取当前用户
        return userService.getCurrentUser(token.substring(7));
    }
}
