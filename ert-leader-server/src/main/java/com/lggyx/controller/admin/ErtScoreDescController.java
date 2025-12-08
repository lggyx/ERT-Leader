package com.lggyx.controller.admin;

import com.lggyx.pojo.dto.UpdateErtScoreDescDTO;
import com.lggyx.pojo.vo.ErtScoreDescVO;
import com.lggyx.result.Result;
import com.lggyx.service.IErtScoreDescService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "ERT得分区间管理", description = "维度得分区间解释管理")
@RestController
public class ErtScoreDescController {
    @Resource
    private IErtScoreDescService ertScoreDescService;
    @Operation(summary = "查询得分区间")
    @GetMapping("/api/admin/ert-score-desc/list")
    public Result<List<ErtScoreDescVO>> getList() {
        return ertScoreDescService.getList();
    }
    @Operation(summary = "更新得分区间描述")
    @PutMapping("/api/admin/ert-score-desc/{id}")
    public Result<Void> update(
            @PathVariable("id") Integer id,
            @RequestBody UpdateErtScoreDescDTO updateErtScoreDescDTO
    ){
        return ertScoreDescService.updates(id, updateErtScoreDescDTO);
    }
}
