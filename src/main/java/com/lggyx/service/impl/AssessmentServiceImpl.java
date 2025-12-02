package com.lggyx.service.impl;

import com.baomidou.mybatisplus.core.toolkit.BeanUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lggyx.context.BaseContext;
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
        String currentAccount = BaseContext.getCurrentAccount();//获取当前用户账号
        Long userId = userMapper.selectOne(Wrappers.<User>lambdaQuery()
                .eq(User::getMobile, currentAccount)
                .or()
                .eq(User::getEmail, currentAccount)).getId();//查询用户id
        if (Objects.equals(type, "QUICK") || Objects.equals(type, "FULL")) {
            // todo:创建测评记录
            //  在assessment表中插入一条记录，在answer表中插入12条记录或者36条记录
            Assessment assessment = new Assessment();
            assessment.setId(null);
            assessment.setUserId(userId);
            assessment.setType(type);
            assessment.setStatus("INIT");
            assessment.setCreatedAt(null);
            assessment.setUpdatedAt(null);
            int count = assessmentMapper.insert(assessment);
            //  通过question表中的sub_dim_code字段进行分组，每组随机选择一个题目
            int questionCount = type.equals("QUICK") ? 12 : 36;
            if (questionCount == 12) {
                //todo: 获取题目ID，ert三个维度每个维度都有3个子维度，e1，e2,e3,e4,r1，r2,r3,r4,t1,t2,t3,t4，每个维度随机选择一个题目
                List<Question> questionList = questionMapper.selectList(Wrappers.lambdaQuery());
                List<Options> optionList = optionMapper.selectList(Wrappers.lambdaQuery());
                List<Question> ques = getRandomOnePerGroup(questionList);
                for (Question question : ques) {
                    Answer answer = new Answer();
                    answer.setAssessmentId(assessment.getId());
                    answer.setQuestionId(question.getId());
                    for (Options option : optionList){
                        if (question.getId().equals(option.getQuestionId())) {
                            answer.setOptionId(option.getId());
                            break;
                        }
                    }
                    answerMapper.insert(answer);
                }
            } else {
                List<Question> questionList = questionMapper.selectList(Wrappers.<Question>lambdaQuery());// 获取所有题目
                for (Question question : questionList) {
                    Answer answer = new Answer();
                    answer.setAssessmentId(assessment.getId());
                    answer.setQuestionId(question.getId());
                    answer.setOptionId(null);
                    answerMapper.insert(answer);
                }
            }
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
     * 获取随机题目12个
     *
     * @param questionList
     * @return
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
        // TODO: 获取测评题目, 通过 assessmentId返回的是一个列表，列表中包含的是题目ID，题目内容，题目选项，题目顺序，题目子维度代码，题目子维度名称
        //  通过answer表中获取
        List<Answer> answerList = answerMapper.selectList(Wrappers.<Answer>lambdaQuery().eq(Answer::getAssessmentId, assessmentId));
        List<Question> getQuestionsList = questionMapper.selectList(Wrappers.<Question>lambdaQuery().in(
                        Question::getId,
                        answerList.stream().map(Answer::getQuestionId).collect(Collectors.toList())
                )
        );
        List<GetQuestionsVO> getQuestionsVOList = getQuestionsList.stream().map(question -> {
            GetQuestionsVO getQuestionsVO = new GetQuestionsVO();
            getQuestionsVO.setQuestionId(Math.toIntExact(question.getId()));
            getQuestionsVO.setSubDimName("");
            getQuestionsVO.setSubDimCode(question.getSubDimCode());
            getQuestionsVO.setContent(question.getContent());
            getQuestionsVO.setSeq(question.getSeq());


            return getQuestionsVO;
        }).collect(Collectors.toList());
        return Result.success(getQuestionsVOList);
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
