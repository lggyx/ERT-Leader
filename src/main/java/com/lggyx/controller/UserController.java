package com.lggyx.controller;


import com.lggyx.pojo.dto.UserDTO;
import com.lggyx.pojo.entity.User;
import com.lggyx.pojo.vo.UserVO;
import com.lggyx.result.Result;
import com.lggyx.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Properties;

/**
 * <p>
 * 测评用户档案 前端控制器
 * </p>
 *
 * @author lggyx
 * @since 2025-11-25
 */
@RestController
@RequestMapping("/api")
@Tag(name = "用户管理", description = "测评用户档案")
public class UserController {
    @Resource
    public IUserService userService;
    @PostMapping("/auth/register")
    @Operation(description = "用户注册")
    public Result<UserVO> register(@RequestBody UserDTO userDTO) {
        UserVO userVO = userService.add(userDTO);
        if (userVO!=null)
            return Result.success(userVO);
        return Result.error("用户注册失败");
    }

}
