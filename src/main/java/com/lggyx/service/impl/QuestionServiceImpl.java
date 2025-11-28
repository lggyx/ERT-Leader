package com.lggyx.service.impl;

import com.lggyx.pojo.dto.CreateQuestionDTO;
import com.lggyx.pojo.entity.Question;
import com.lggyx.mapper.QuestionMapper;
import com.lggyx.pojo.vo.CreateQuestionVO;
import com.lggyx.result.PageResult;
import com.lggyx.result.Result;
import com.lggyx.service.IQuestionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
    /**
     * 分页查询
     *
     * @param page
     * @param pageSize
     * @param subDimCode
     * @return
     */
    @Override
    public Result<PageResult> page(Integer page, Integer pageSize, String subDimCode) {
        return null;
    }
    /**
     * 创建题目
     *
     * @param createQuestionDTO 题目参数
     * @return
     */
    @Override
    public Result<CreateQuestionVO> create(CreateQuestionDTO createQuestionDTO) {
        return null;
    }
    /**
     * 修改题目
     *
     * @param questionId 题目ID
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
