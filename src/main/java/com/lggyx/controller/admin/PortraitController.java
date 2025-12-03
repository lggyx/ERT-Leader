package com.lggyx.controller.admin;

import com.lggyx.pojo.dto.UpdatePortraitDTO;
import com.lggyx.pojo.vo.PortraitVO;
import com.lggyx.result.Result;
import com.lggyx.service.IPortraitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "27项领导力画像配置", description = "画像管理")
@RestController
public class PortraitController {
    @Resource
    private IPortraitService portraitService;

    @Operation(summary = "查询画像列表")
    @GetMapping("/api/admin/portrait/list")
    public Result<List<PortraitVO>> getList() {
        return portraitService.getList();
    }

    @Operation(summary = "更新画像描述")
    @PutMapping("/api/admin/portrait/{id}")
    public Result<Void> update(
            @PathVariable("id") Integer id,
            @RequestBody UpdatePortraitDTO updatePortraitDTO
    ) {
        return portraitService.updates(id, updatePortraitDTO);
    }



}
