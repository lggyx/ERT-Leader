package com.lggyx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lggyx.enumeration.SuccessCode;
import com.lggyx.dto.CreateQuestionDTO;
import com.lggyx.entity.Question;
import com.lggyx.mapper.QuestionMapper;
import com.lggyx.vo.CreateQuestionVO;
import com.lggyx.result.PageResult;
import com.lggyx.result.Result;
import com.lggyx.service.IQuestionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 测评题库 服务实现类
 * </p>
 *
 * @author lggyx
 * @since 2025-11-25
 */
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements IQuestionService {
    @Resource
    private QuestionMapper questionMapper;

    /**
     * 分页查询
     *
     * @param page       页码
     * @param pageSize   每页数量
     * @param subDimCode 子维度编码
     * @param keyword    搜索关键词
     * @return Result
     */
    @Override
    public Result<PageResult> page(Integer page, Integer pageSize, String subDimCode, String keyword) {
        Page<Question> questionPage = new Page<>(page, pageSize);
        QueryWrapper<Question> queryWrapper = new QueryWrapper<>();
        if (subDimCode != null && !subDimCode.trim().isEmpty()) {
            queryWrapper.eq("sub_dim_code", subDimCode);
        }
        if (keyword != null && !keyword.trim().isEmpty()) {
            queryWrapper.and(wrapper -> wrapper
                    .like("content", keyword));
        }
        IPage<Question> resultPage = questionMapper.selectPage(questionPage, queryWrapper);
        PageResult pageResult = new PageResult();
        pageResult.setTotal(resultPage.getTotal());
        pageResult.setRecords(resultPage.getRecords());
        return Result.success(SuccessCode.SUCCESS, pageResult);
    }

    /**
     * 创建题目
     *
     * @param createQuestionDTO 题目参数
     * @return Result
     */
    @Override
    public Result<CreateQuestionVO> create(CreateQuestionDTO createQuestionDTO) {
        Question question = new Question();
        BeanUtils.copyProperties(createQuestionDTO, question);
        int insert = questionMapper.insert(question);
        CreateQuestionVO createQuestionVO = new CreateQuestionVO();
        createQuestionVO.setQuestionId(question.getId());
        return insert > 0 ? Result.success(SuccessCode.SUCCESS, createQuestionVO) : Result.error("创建题目失败");
    }

    /**
     * 修改题目
     *
     * @param questionId        题目ID
     * @param createQuestionDTO 修改参数
     * @return Result
     */
    @Override
    public Result<Void> update(Long questionId, CreateQuestionDTO createQuestionDTO) {
        Question question = new Question();
        BeanUtils.copyProperties(createQuestionDTO, question);
        int count = questionMapper.update(question,
                Wrappers.<Question>lambdaQuery().eq(Question::getId, questionId)
        );
        return count > 0 ? Result.success(SuccessCode.SUCCESS) : Result.error("修改题目失败");
    }

    /**
     * 删除题目
     *
     * @param questionId 删除的题目ID
     * @return Result
     */
    @Override
    public Result<Void> delete(Long questionId) {
        int count = questionMapper.delete(
                Wrappers.<Question>lambdaQuery().eq(Question::getId, questionId)
        );
        return count > 0 ? Result.success(SuccessCode.SUCCESS) : Result.error("删除题目失败");
    }


}
