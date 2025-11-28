package com.lggyx.service;

import com.lggyx.pojo.dto.CreateQuestionDTO;
import com.lggyx.pojo.entity.Question;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lggyx.pojo.vo.CreateQuestionVO;
import com.lggyx.result.PageResult;
import com.lggyx.result.Result;

/**
 * <p>
 * 测评题库 服务类
 * </p>
 *
 * @author lggyx
 * @since 2025-11-25
 */
public interface IQuestionService extends IService<Question> {

    Result<PageResult> page(Integer page, Integer pageSize, String subDimCode);

    Result<CreateQuestionVO> create(CreateQuestionDTO createQuestionDTO);

    Result<Void> update(Long questionId, CreateQuestionDTO createQuestionDTO);

    Result<Void> delete(Long questionId);
}
