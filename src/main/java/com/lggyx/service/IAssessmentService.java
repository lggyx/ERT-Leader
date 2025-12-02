package com.lggyx.service;

import com.lggyx.pojo.dto.AnswerDTO;
import com.lggyx.pojo.dto.CreateAssessmentDTO;
import com.lggyx.pojo.entity.Assessment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lggyx.pojo.vo.*;
import com.lggyx.result.PageResult;
import com.lggyx.result.Result;

import java.util.List;

/**
 * <p>
 * 用户测评记录 服务类
 * </p>
 *
 * @author lggyx
 * @since 2025-11-25
 */
public interface IAssessmentService extends IService<Assessment> {

    Result<CreateAssessmentVO> create(CreateAssessmentDTO createAssessmentDTO);

    Result<List<GetQuestionsVO>> getQuestions(Long assessmentId);

    Result<AnswerVO> answer(Long assessmentId, AnswerDTO answerDTO);

    Result<CompleteVO> complete(Long assessmentId);

    Result<ResultVO> result(Long assessmentId);

    Result<PageResult> getHistory(Integer page, Integer pageSize);
}
