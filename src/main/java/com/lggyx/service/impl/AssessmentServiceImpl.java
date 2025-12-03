package com.lggyx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lggyx.context.BaseContext;
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
    @Resource
    private SubDimensionMapper subDimensionMapper;
    @Resource
    private ErtScoreDescMapper ertScoreDescMapper;
    @Resource
    private SubScoreActionMapper subScoreActionMapper;
    @Resource
    private PortraitMapper portraitMapper;

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
            getQuestionsVO.setOptions(optionMapper.selectList(
                    Wrappers.<Options>lambdaQuery().eq(Options::getQuestionId, question.getId())).stream().map(option -> {
                        GetQuestionsVO.Options options = new GetQuestionsVO.Options();
                        options.setOptionId(Math.toIntExact(option.getId()));
                        options.setLabel(option.getLabel());
                        options.setScore(option.getScore());
                        options.setSeq(option.getSeq());
                        return options;
            }).collect(Collectors.toList()));
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
        //检查题目是否全部答完
        int remainQuestions = Math.toIntExact(answerMapper.selectCount(Wrappers.<Answer>lambdaQuery()
                .eq(Answer::getAssessmentId, assessmentId)
                .eq(Answer::getAnswered, false)
        ));
        int eScore, rScore, tScore;
        eScore = getScore('E', assessmentId);
        rScore = getScore('R', assessmentId);
        tScore = getScore('T', assessmentId);
        log.info(" remainQuestions: {}", remainQuestions);
        if (remainQuestions > 0) {
            return Result.error("请完成所有题目");
        } else {
            int assessmentCount = assessmentMapper.update(
                    Wrappers.<Assessment>lambdaUpdate()
                            .eq(Assessment::getId, assessmentId)
                            .set(Assessment::getStatus, "DONE")
                            .set(Assessment::getEScore, eScore)
                            .set(Assessment::getRScore, rScore)
                            .set(Assessment::getTScore, tScore)
                            .set(Assessment::getPortraitId, getPortraitId(getScore('E', assessmentId),
                                    getScore('R', assessmentId), getScore('T', assessmentId)))
            );
            if (assessmentCount > 0) {
                CompleteVO completeVO = new CompleteVO();
                completeVO.setAssessmentId(Math.toIntExact(assessmentId));
                completeVO.setStatus("DONE");
                completeVO.setEScore(eScore);
                completeVO.setRScore(rScore);
                completeVO.setTScore(tScore);
                completeVO.setPortraitId(getPortraitId(completeVO.getEScore(), completeVO.getRScore(),
                        completeVO.getTScore()));
                completeVO.setPortraitDesc(getPortraitDesc(completeVO.getPortraitId()));
                return Result.success(SuccessCode.SUCCESS, completeVO);
            } else {
                return Result.error("请选择正确的测评记录ID");
            }
        }
    }

    /**
     * 获取得分
     *
     * @param dimCode      维度代码
     * @param assessmentId 测评记录ID
     * @return int
     */
    public int getScore(char dimCode, Long assessmentId) {
        List<Answer> answerList = answerMapper.selectList(Wrappers.<Answer>lambdaQuery().eq(
                Answer::getAssessmentId, assessmentId
        ));
        List<Answer> scoreList = answerList.stream()
                .filter(answer -> {
                    Question question = questionMapper.selectById(answer.getQuestionId());
                    return question.getSubDimCode().charAt(0) == dimCode;
                }).toList();
        int score = scoreList.stream().mapToInt(answer -> {
            Options options = optionMapper.selectById(answer.getOptionId());
            return options.getScore();
        }).sum();
        // 根据题目数量计算标准化得分
        int totalQuestions = answerList.size() / 3;
        int maxScore = totalQuestions * 5; // 最高总分
        double normalizedScore = (double) (score - totalQuestions) / (maxScore - totalQuestions);
        // 标准化得分公式
        return (int) (normalizedScore * 100);

    }

    /**
     * 获取画像ID
     *
     * @param eScore E评分
     * @param rScore R评分
     * @param tScore T评分
     * @return int
     */
    public int getPortraitId(int eScore, int rScore, int tScore) {
        //先计算e画像在数据库对应最小得分到最大得分那个区间0-50为L，51-100为H
        String eLevel = (eScore >= 51 ? "H" : "L");
        String rLevel = (rScore >= 51 ? "H" : "L");
        String tLevel = (tScore >= 51 ? "H" : "L");
        return portraitMapper.selectOne(
                Wrappers.<Portrait>lambdaQuery()
                        .eq(Portrait::getELevel, eLevel)
                        .eq(Portrait::getRLevel, rLevel)
                        .eq(Portrait::getTLevel, tLevel)
        ).getId();
    }

    /**
     * 获取画像描述
     *
     * @param portraitId 画像ID
     * @return String
     */
    public String getPortraitDesc(int portraitId) {
        return portraitMapper.selectById(portraitId).getDescription();
    }

    /**
     * 获取测评结果详情
     *
     * @param assessmentId 测评记录ID
     * @return Result<ResultVO>
     */
    @Override
    public Result<ResultVO> result(Long assessmentId) {
        ResultVO resultVO = new ResultVO();
        Assessment assessment = assessmentMapper.selectById(assessmentId);

        resultVO.setAssessmentId(Math.toIntExact(assessmentId));
        resultVO.setUserName(userMapper.selectById(assessment.getUserId()).getName());
        resultVO.setType(assessment.getType());
        resultVO.setCompletedAt(assessment.getUpdatedAt());


        ResultVO.DimensionScores dimensionScores = new ResultVO.DimensionScores();
        ResultVO.E e = new ResultVO.E();
        e.setScore(assessment.getEScore());
        e.setLevel(assessment.getEScore() >= 51 ? "H" : "L");
        e.setDescription(getDimensionDesc('E', assessment.getEScore()));
        ResultVO.R r = new ResultVO.R();
        r.setScore(assessment.getRScore());
        r.setLevel(assessment.getRScore() >= 51 ? "H" : "L");
        r.setDescription(getDimensionDesc('R', assessment.getRScore()));
        ResultVO.T t = new ResultVO.T();
        t.setScore(assessment.getTScore());
        t.setLevel(assessment.getTScore() >= 51 ? "H" : "L");
        t.setDescription(getDimensionDesc('T', assessment.getTScore()));
        dimensionScores.setE(e);
        dimensionScores.setR(r);
        dimensionScores.setT(t);
        resultVO.setDimensionScores(dimensionScores);


        Portrait portrait = portraitMapper.selectById(assessment.getPortraitId());
        String Code = portrait.getELevel() + portrait.getRLevel() + portrait.getTLevel();
        ResultVO.PortraitVO portraitVO = new ResultVO.PortraitVO();
        portraitVO.setCode(Code);
        portraitVO.setDescription(portrait.getDescription());
        resultVO.setPortrait(portraitVO);

        //todo: 获取子维度得分，获取子维度得分计划等
        List<SubDimension> subDimensionList = subDimensionMapper.selectList(Wrappers.<SubDimension>lambdaQuery().in(
                SubDimension::getDimensionCode, assessment.getType().charAt(0)
        ));
        for (SubDimension subDimension : subDimensionList) {
            ResultVO.SubDimensionScores subDimensionScores = new ResultVO.SubDimensionScores();
            subDimensionScores.setCode(subDimension.getCode());
            subDimensionScores.setName(subDimension.getName());
            subDimensionScores.setScore(getScore(subDimension.getCode().charAt(0), assessmentId));
            subDimensionScores.setDimension(getScore(subDimension.getCode().charAt(0), assessmentId) >= 51 ? "H" : "L");
            subDimensionScores.setActionPlan(subScoreActionMapper.selectById(getScore(subDimension.getCode().charAt(0),
                    assessmentId)).getActionPlan());
            resultVO.getSubDimensionScores().add(subDimensionScores);
        }
        return Result.success(SuccessCode.SUCCESS, resultVO);

    }

    /**
     * 获取维度描述
     *
     * @param dimCode 维度代码
     * @param score   分数
     * @return String
     */
    public String getDimensionDesc(char dimCode, int score) {
        QueryWrapper<ErtScoreDesc> queryWrapper = new QueryWrapper<>();
        queryWrapper.le("min_score", score)
                .ge("max_score", score)
                .eq("dimension_code", dimCode);
        return ertScoreDescMapper.selectOne(queryWrapper).getDescription();
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
        String account = BaseContext.getCurrentAccount();
        Long userId = userMapper.selectOne(
                Wrappers.<User>lambdaQuery().eq(User::getMobile, account)
                        .or()
                        .eq(User::getEmail, account)
        ).getId();

        Page<Assessment> pageParam = new Page<>(page, pageSize);
        Page<Assessment> pageResult = this.page(pageParam, Wrappers.<Assessment>lambdaQuery().eq(Assessment::getUserId,
                userId));
        return Result.success(SuccessCode.SUCCESS, new PageResult(pageResult.getTotal(), pageResult.getRecords()));
    }
}
