package com.lggyx.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lggyx
 * @since 2025-11-25
 */
@RestController
@RequestMapping("/user")
@Tag(name = "用户管理接口",description = "用户接口")
public class UserController {
    @GetMapping
    @Operation(summary = "用户接口",description = "用户接口")
    public String test(){
        return "user";
    }

}

