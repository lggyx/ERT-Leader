package com.lggyx.controller.admin;

import com.lggyx.pojo.dto.SubDimensionDTO;
import com.lggyx.pojo.entity.Dimension;
import com.lggyx.pojo.entity.SubDimension;
import com.lggyx.pojo.vo.SubDimensionVO;
import com.lggyx.result.Result;
import com.lggyx.service.ISubDimensionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "子维度配置管理", description = "维度管理")
@RestController
public class SubDimensionController {
    @Resource
    private ISubDimensionService subDimensionService;

    @Operation(summary = "查询子维度列表")
    @GetMapping("/api/admin/sub-dimension/list")
    public Result<List<SubDimensionVO>> getList(
            @RequestParam(name = "dimensionCode", required = false) String dimensionCode
    ) {
        return subDimensionService.getList(dimensionCode);
    }

    @Operation(summary = "更新子维度")
    @PutMapping("/api/admin/sub-dimension/{code}")
    public Result<Void> update(
            @RequestParam("code") String code,
            @RequestBody SubDimensionDTO subDimensionDTO
            ) {
        return subDimensionService.update(code, subDimensionDTO);
    }
}
