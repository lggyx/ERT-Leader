package com.lggyx.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lggyx.context.BaseContext;
import com.lggyx.enumeration.ErrorCode;
import com.lggyx.enumeration.SuccessCode;
import com.lggyx.mapper.*;
import com.lggyx.pojo.dto.AnswerDTO;
import com.lggyx.pojo.dto.CreateAssessmentDTO;
import com.lggyx.pojo.entity.*;
import com.lggyx.pojo.vo.*;
import com.lggyx.result.PageResult;
import com.lggyx.result.Result;
import com.lggyx.service.IAssessmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
    @Resource
    private QuestionMapper questionMapper;
    @Resource
    private AnswerMapper answerMapper;
    @Resource
    private OptionsMapper optionMapper;

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
                .eq(User::getMobile, currentAccount).or().eq(User::getEmail, currentAccount)).getId();
        if (Objects.equals(type, "QUICK") || Objects.equals(type, "FULL")) {
            Assessment assessment = new Assessment();
            assessment.setId(null);
            assessment.setUserId(userId);
            assessment.setType(type);
            assessment.setStatus("INIT");
            assessment.setCreatedAt(null);
            assessment.setUpdatedAt(null);
            int count = assessmentMapper.insert(assessment);
            //以上代码无问题
            int questionCount = type.equals("QUICK") ? 12 : 36;
            List<Question> questionList = questionMapper.selectList(Wrappers.lambdaQuery());// 获取所有题目
            List<Options> optionList = optionMapper.selectList(Wrappers.lambdaQuery());
            if (questionCount == 12) {
                List<Question> ques = getRandomOnePerGroup(questionList);
                insertAnswer(assessment, optionList, ques);
            } else {
                insertAnswer(assessment, optionList, questionList);
            }
            CreateAssessmentVO createAssessmentVO = new CreateAssessmentVO();
            createAssessmentVO.setAssessmentId(assessment.getId());
            createAssessmentVO.setType(type);
            createAssessmentVO.setStatus("INIT");
            createAssessmentVO.setQuestionCount(type.equals("QUICK") ? 12 : 36);
            if (count > 0)
                return Result.success(SuccessCode.SUCCESS, createAssessmentVO);
            else return Result.error("创建测评记录失败");
        } else return Result.error("请选择正确的测评类型");
    }

    /**
     * 插入答案
     *
     * @param assessment 测评记录
     * @param optionList 选项列表
     * @param ques       获取的题目列表
     */
    public void insertAnswer(Assessment assessment, List<Options> optionList, List<Question> ques) {
        for (Question question : ques) {
            Answer answer = new Answer();
            answer.setAssessmentId(assessment.getId());
            answer.setQuestionId(question.getId());
            for (Options option : optionList) {
                if (option.getQuestionId().equals(question.getId())) {
                    answer.setOptionId(option.getId());
                    break;
                }
            }
            answerMapper.insert(answer);
        }
    }

    /**
     * 获取随机题目12个
     *
     * @param questionList 获取的题目列表
     * @return List<Question>
     */
    public List<Question> getRandomOnePerGroup(List<Question> questionList) {
        // 按 sub_dim_code 分组
        Map<String, List<Question>> groupedQuestions = questionList.stream()
                .collect(Collectors.groupingBy(Question::getSubDimCode));

        // 每组随机选择一个题目
        List<Question> result = new ArrayList<>();
        for (List<Question> group : groupedQuestions.values()) {
            Collections.shuffle(group); // 打乱顺序
            result.add(group.get(0)); // 取第一个
        }
        return result;
    }

    /**
     * 根据测评记录ID获取测评题目
     *
     * @param assessmentId 测评记录ID
     * @return Result<List < GetQuestionsVO>
     */
    @Override
    public Result<List<GetQuestionsVO>> getQuestions(Long assessmentId) {
        if (assessmentId == null || assessmentId <= 0) {
            return Result.error("请选择正确的测评记录ID");
        }
        //todo 需要添加查找不到的处理
        List<Answer> answerList = answerMapper.selectList(Wrappers.<Answer>lambdaQuery().eq(Answer::getAssessmentId,
                assessmentId));
        if (answerList.isEmpty()) {
            return Result.error("请选择正确的测评记录ID");
        }
        List<Question> getQuestionsList = questionMapper.selectList(Wrappers.<Question>lambdaQuery().in(Question::getId,
                answerList.stream().map(Answer::getQuestionId).collect(Collectors.toList())));
        List<GetQuestionsVO> getQuestionsVOList = getQuestionsList.stream().map(question -> {
            GetQuestionsVO getQuestionsVO = new GetQuestionsVO();
            getQuestionsVO.setQuestionId(Math.toIntExact(question.getId()));
            getQuestionsVO.setSubDimName("");
            getQuestionsVO.setSubDimCode(question.getSubDimCode());
            getQuestionsVO.setContent(question.getContent());
            getQuestionsVO.setSeq(question.getSeq());
            return getQuestionsVO;
        }).collect(Collectors.toList());
        return Result.success(SuccessCode.SUCCESS, getQuestionsVOList);
    }

    /**
     * 提交答案
     *
     * @param assessmentId 测评记录ID
     * @param answerDTO    测评结果
     * @return Result<AnswerVO>
     */
    @Override
    public Result<AnswerVO> answer(Long assessmentId, AnswerDTO answerDTO) {
        //根据assessmentId,questionId查找记录，如果有则更新，没有则插入并提示
        int updateCount = answerMapper.update(
                Wrappers.<Answer>lambdaUpdate()
                        .eq(Answer::getAssessmentId, assessmentId)
                        .eq(Answer::getQuestionId, answerDTO.getQuestionId())
                        .set(Answer::getOptionId, answerDTO.getOptionId())
                        .set(Answer::getAnswered, true)
        );
        AnswerVO answerVO = new AnswerVO();
        answerVO.setAssessmentId(Math.toIntExact(assessmentId));
        answerVO.setQuestionId(Math.toIntExact(answerDTO.getQuestionId()));
        answerVO.setAnswered(updateCount > 0);
        answerVO.setRemainQuestions(Math.toIntExact(
                answerMapper.selectCount(Wrappers.<Answer>lambdaQuery()
                        .eq(Answer::getAssessmentId, assessmentId)
                        .eq(Answer::getAnswered, false)
                )));
        return Result.success(SuccessCode.SUCCESS, answerVO);
    }

    /**
     * 完成测评并计算得分
     *
     * @param assessmentId 测评记录ID
     * @return Result<CompleteVO>
     */
    @Override
    public Result<CompleteVO> complete(Long assessmentId) {
        return null;
    }

    /**
     * 获取测评结果详情
     *
     * @param assessmentId 测评记录ID
     * @return Result<ResultVO>
     */
    @Override
    public Result<ResultVO> result(Long assessmentId) {
        return null;
    }

    /**
     * 获取测评历史
     *
     * @param page     页码
     * @param pageSize 每页数量
     * @return Result<PageResult>
     */
    @Override
    public Result<PageResult> getHistory(Integer page, Integer pageSize) {
        return null;
    }
}
