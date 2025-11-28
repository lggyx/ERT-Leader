package com.lggyx.controller.admin;

import com.lggyx.pojo.dto.CreateQuestionDTO;
import com.lggyx.pojo.vo.CreateQuestionVO;
import com.lggyx.result.PageResult;
import com.lggyx.result.Result;
import com.lggyx.service.IQuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "题目管理", description = "题目管理")
public class QuestionController {
    @Resource
    private IQuestionService questionService;

    @Operation(summary = "分页查询题目", description = "")
    @GetMapping("/api/admin/question/page")
    public Result<PageResult> page(
            @RequestParam(value = "page", defaultValue = "1") Integer page, // 当前页码
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize, // 每页显示数量
            @RequestParam(value = "subDimCode", defaultValue = "", required = false) String subDimCode, // 筛选子维度编码
            @RequestParam(value = "keyword", defaultValue = "", required = false) String keyword // 筛选题干关键词
    ) {
        return questionService.page(page, pageSize, subDimCode);
    }

    @Operation(summary = "创建题目", description = "")
    @PostMapping("/api/admin/question")
    public Result<CreateQuestionVO> create(@RequestBody CreateQuestionDTO createQuestionDTO) {
        return questionService.create(createQuestionDTO);
    }

    @Operation(summary = "更新题目", description = "")
    @PutMapping("/api/admin/question/{questionId}")
    public Result<Void> update(@PathVariable("questionId") Long questionId, @RequestBody CreateQuestionDTO createQuestionDTO) {
        return questionService.update(questionId, createQuestionDTO);
    }

    @Operation(summary = "删除题目", description = "级联删除关联的选项和答案数据")
    @DeleteMapping("/api/admin/question/{questionId}")
    public Result<Void> delete(@PathVariable("questionId") Long questionId) {
        return questionService.delete(questionId);
    }
}
