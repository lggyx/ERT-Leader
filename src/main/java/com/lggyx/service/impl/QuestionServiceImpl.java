package com.lggyx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lggyx.enumeration.SuccessCode;
import com.lggyx.pojo.dto.CreateQuestionDTO;
import com.lggyx.pojo.entity.Question;
import com.lggyx.mapper.QuestionMapper;
import com.lggyx.pojo.vo.CreateQuestionVO;
import com.lggyx.result.PageResult;
import com.lggyx.result.Result;
import com.lggyx.service.IQuestionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
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
     * @return  Result
     */
    @Override
    public Result<CreateQuestionVO> create(CreateQuestionDTO createQuestionDTO) {
        return null;
    }

    /**
     * 修改题目
     *
     * @param questionId        题目ID
     * @param createQuestionDTO 修改参数
     * @return
     */
    @Override
    public Result<Void> update(Long questionId, CreateQuestionDTO createQuestionDTO) {
        return null;
    }

    /**
     * 删除题目
     *
     * @param questionId 删除的题目ID
     * @return
     */
    @Override
    public Result<Void> delete(Long questionId) {
        return null;
    }


}
