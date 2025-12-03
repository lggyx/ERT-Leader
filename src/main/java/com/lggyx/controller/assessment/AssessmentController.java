package com.lggyx.controller.assessment;


import com.lggyx.pojo.dto.AnswerDTO;
import com.lggyx.pojo.dto.CreateAssessmentDTO;
import com.lggyx.pojo.vo.*;
import com.lggyx.result.PageResult;
import com.lggyx.result.Result;
import com.lggyx.service.IAssessmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 用户测评记录 前端控制器
 * </p>
 *
 * @author lggyx
 * @since 2025-11-25
 */
@Tag(name = "测评流程模块", description = "用户测评记录")
@RestController
public class AssessmentController {
    @Resource
    public IAssessmentService assessmentService;

    @Operation(summary = "创建测评记录", description = "创建新的测评任务，极速测评随机抽取每子维度1题")
    @PostMapping("/api/assessment/create")
    public Result<CreateAssessmentVO> create(@RequestBody CreateAssessmentDTO createAssessmentDTO) {
        return assessmentService.create(createAssessmentDTO);
    }

    @Operation(summary = "获取测评题目")
    @PostMapping("/api/assessment/{assessmentId}/questions")
    public Result<List<GetQuestionsVO>> getQuestions(@PathVariable Long assessmentId) {
        return assessmentService.getQuestions(assessmentId);
    }

    @Operation(summary = "提交答案", description = "单题提交，支持返回剩余题数；重复提交同一题则覆盖答案")
    @PostMapping("/api/assessment/{assessmentId}/answer")
    public Result<AnswerVO> answer(@PathVariable Long assessmentId, @RequestBody AnswerDTO answerDTO) {
        return assessmentService.answer(assessmentId, answerDTO);
    }

    @Operation(summary = "完成测评并计算得分", description = "必须答完所有题目才能调用，自动计算三大维度得分并匹配画像")
    @PostMapping("/api/assessment/{assessmentId}/complete")
    public Result<CompleteVO> complete(@PathVariable Long assessmentId) {
        return assessmentService.complete(assessmentId);
    }

    @Operation(summary = "获取测评结果详情")
    @GetMapping("/api/assessment/{assessmentId}/result")
    public Result<ResultVO> result(@PathVariable Long assessmentId) {
        return assessmentService.result(assessmentId);
    }

    @Operation(summary = "查询我的测评历史")
    @GetMapping("/api/assessment/history")
    public Result<PageResult> getHistory(
            @RequestParam(name = "page", required = false, defaultValue = "1")
            Integer page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10")
            Integer pageSize
    ){
        return assessmentService.getHistory(page, pageSize);
    }

}
