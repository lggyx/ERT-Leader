package com.lggyx.controller.admin;

import com.lggyx.result.PageResult;
import com.lggyx.result.Result;
import com.lggyx.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "用户管理", description = "用户管理")
public class AdminController {
    @Resource
    private IUserService userService;

    @Operation(summary = "分页查询用户列表", description = "")
    @GetMapping("/api/admin/user/page")
    public Result<PageResult> page(
            @RequestParam(value = "page", defaultValue = "1") Integer page, // 当前页码
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize, // 每页显示数量
            @RequestParam(value = "status", defaultValue = "", required = false) String status, // 状态
            @RequestParam(value = "role", defaultValue = "", required = false) String role, // 角色
            @RequestParam(value = "keyword", defaultValue = "", required = false) String keyword // 关键词
    ) {
        return userService.page(page, pageSize, status, role, keyword);
    }

    @Operation(summary = "启用/禁用用户", description = "")
    @PutMapping("/api/admin/user/{userId}/status")
    public Result<Void> updateStatus(
            @PathVariable("userId") Long userId,
            @RequestParam("status") Integer status
    ) {
        return userService.updateStatus(userId, status);
    }

    @Operation(summary = "设置用户角色", description = "")
    @PutMapping("/api/admin/user/{userId}/role")
    public Result<Void> updateRole(
            @PathVariable("userId") Long userId,
            @RequestParam("role") String role
    ) {
        return userService.updateRole(userId, role);
    }


}
