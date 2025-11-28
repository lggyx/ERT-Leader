package com.lggyx.service.impl;

import com.lggyx.pojo.dto.AnswerDTO;
import com.lggyx.pojo.dto.CreateAssessmentDTO;
import com.lggyx.pojo.entity.Assessment;
import com.lggyx.mapper.AssessmentMapper;
import com.lggyx.pojo.vo.*;
import com.lggyx.result.PageResult;
import com.lggyx.result.Result;
import com.lggyx.service.IAssessmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户测评记录 服务实现类
 * </p>
 *
 * @author lggyx
 * @since 2025-11-25
 */
@Service
public class AssessmentServiceImpl extends ServiceImpl<AssessmentMapper, Assessment> implements IAssessmentService {
    /**
     * 创建测评记录
     * @return
     */
    @Override
    public Result<CreateAssessmentVO> create(CreateAssessmentDTO createAssessmentDTO) {
        String type = createAssessmentDTO.getType();
        return null;
    }

    /**
     * 根据测评记录ID获取测评题目
     * @param assessmentId
     * @return
     */
    @Override
    public Result<List<GetQuestionsVO>> getQuestions(Long assessmentId) {
        // TODO: 获取测评题目, 通过 assessmentId返回的是一个列表，列表中包含的是题目ID，题目内容，题目选项，题目顺序，题目子维度代码，题目子维度名称
        return null;
    }
    /**
     * 提交测评结果
     * @param assessmentId
     * @param answerDTOs
     * @return
     */
    @Override
    public Result<AnswerVO> answer(Long assessmentId, List<AnswerDTO> answerDTOs) {
        return null;
    }

    /**
     * 完成测评并计算得分
     * @param assessmentId
     * @return
     */
    @Override
    public Result<CompleteVO> complete(Long assessmentId) {
        return null;
    }
    /**
     * 获取测评结果详情
     * @param assessmentId
     * @return
     */
    @Override
    public Result<ResultVO> result(Long assessmentId) {
        return null;
    }
    /**
     * 获取测评历史
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public Result<PageResult> getHistory(Integer page, Integer pageSize) {
        return null;
    }
}
