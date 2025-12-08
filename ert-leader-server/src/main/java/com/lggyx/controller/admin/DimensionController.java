package com.lggyx.controller.admin;

import com.lggyx.pojo.dto.DimensionDTO;
import com.lggyx.pojo.vo.DimensionVO;
import com.lggyx.result.Result;
import com.lggyx.service.IDimensionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "维度配置管理", description = "维度管理")
@RestController
public class DimensionController {
    @Resource
    private IDimensionService dimensionService;
    @Operation(summary = "查询三大能力维度", description = "")
    @GetMapping("/api/admin/dimension/list")
    public Result<List<DimensionVO>> list() {
        return dimensionService.getList();
    }
    @Operation(summary = "更新维度描述", description = "")
    @PutMapping("/api/admin/dimension/{code}")
    public Result<Void> updateDesc(
            @PathVariable("code") String code,
            @RequestParam("dimension")DimensionDTO dimensionDTO
            ) {
        return dimensionService.updateDesc(code, dimensionDTO);
    }
}
