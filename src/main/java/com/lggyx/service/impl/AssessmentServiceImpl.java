package com.lggyx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lggyx.context.BaseContext;
import com.lggyx.mapper.UserMapper;
import com.lggyx.pojo.dto.AnswerDTO;
import com.lggyx.pojo.dto.CreateAssessmentDTO;
import com.lggyx.pojo.entity.Assessment;
import com.lggyx.mapper.AssessmentMapper;
import com.lggyx.pojo.entity.User;
import com.lggyx.pojo.vo.*;
import com.lggyx.result.PageResult;
import com.lggyx.result.Result;
import com.lggyx.service.IAssessmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lggyx.service.IUserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 用户测评记录 服务实现类
 * </p>
 *
 * @author lggyx
 * @since 2025-11-25
 */
@Service
@Slf4j
public class AssessmentServiceImpl extends ServiceImpl<AssessmentMapper, Assessment> implements IAssessmentService {
    @Resource
    private AssessmentMapper assessmentMapper;
    @Resource
    private UserMapper userMapper;

    /**
     * 创建测评记录
     *
     * @return Result<CreateAssessmentVO>
     */
    @Override
    public Result<CreateAssessmentVO> create(CreateAssessmentDTO createAssessmentDTO) {
        String type = createAssessmentDTO.getType(); // QUICK-极速测评(12题), FULL-完整测评(36题)
        String currentAccount = BaseContext.getCurrentAccount();
        Long userId = userMapper.selectOne(Wrappers.<User>lambdaQuery()
                .eq(User::getMobile, currentAccount)
                .or()
                .eq(User::getEmail, currentAccount)).getId();
        if (Objects.equals(type, "QUICK") || Objects.equals(type, "FULL")) {
            Assessment assessment = new Assessment();
            assessment.setId(null);
            assessment.setUserId(userId);
            assessment.setType(type);
            assessment.setStatus("INIT");
            assessment.setCreatedAt(null);
            assessment.setUpdatedAt(null);
            int count = assessmentMapper.insert(assessment);
            CreateAssessmentVO createAssessmentVO = new CreateAssessmentVO();
            createAssessmentVO.setAssessmentId(assessment.getId());
            createAssessmentVO.setType(type);
            createAssessmentVO.setStatus("INIT");
            createAssessmentVO.setQuestionCount(type.equals("QUICK") ? 12 : 36);
            if (count > 0)
                return Result.success(createAssessmentVO);
            else return Result.error("创建测评记录失败");
        } else return Result.error("请选择正确的测评类型");
    }

    /**
     * 根据测评记录ID获取测评题目
     *
     * @param assessmentId 测评记录ID
     * @return Result<List<GetQuestionsVO>
     */
    @Override
    public Result<List<GetQuestionsVO>> getQuestions(Long assessmentId) {
        // TODO: 获取测评题目, 通过 assessmentId返回的是一个列表，列表中包含的是题目ID，题目内容，题目选项，题目顺序，题目子维度代码，题目子维度名称
        return null;
    }

    /**
     * 提交测评结果
     *
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
     *
     * @param assessmentId
     * @return
     */
    @Override
    public Result<CompleteVO> complete(Long assessmentId) {
        return null;
    }

    /**
     * 获取测评结果详情
     *
     * @param assessmentId
     * @return
     */
    @Override
    public Result<ResultVO> result(Long assessmentId) {
        return null;
    }

    /**
     * 获取测评历史
     *
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public Result<PageResult> getHistory(Integer page, Integer pageSize) {
        return null;
    }
}
