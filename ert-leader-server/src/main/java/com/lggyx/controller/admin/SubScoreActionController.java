package com.lggyx.controller.admin;

import com.lggyx.dto.UpdateSubScoreActionDTO;
import com.lggyx.vo.SubScoreActionVO;
import com.lggyx.result.Result;
import com.lggyx.service.ISubScoreActionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "子维度行动方案管理", description = "子维度得分行动方案管理")
public class SubScoreActionController {
    @Resource
    private ISubScoreActionService subScoreActionService;

    @Operation(summary = "查询行动方案")
    @GetMapping("/api/admin/sub-score-action/list")
    public Result<List<SubScoreActionVO>> getList(
            @RequestParam(name = "subDimCode", required = false) String subDimCode // 子维度编码
    ) {
        return subScoreActionService.getList(subDimCode);
    }

    @Operation(summary = "更新行动方案")
    @PutMapping("/api/admin/sub-score-action/{id}")
    public Result<Void> update(
            @PathVariable("id") Integer id,
            @RequestBody UpdateSubScoreActionDTO updateSubScoreActionDTO
    ) {
        return subScoreActionService.updates(id, updateSubScoreActionDTO);
    }
}
