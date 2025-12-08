package com.lggyx.service;

import com.lggyx.dto.UpdateSubScoreActionDTO;
import com.lggyx.entity.SubScoreAction;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lggyx.vo.SubScoreActionVO;
import com.lggyx.result.Result;

import java.util.List;

/**
 * <p>
 * 子维度得分行动方案 服务类
 * </p>
 *
 * @author lggyx
 * @since 2025-11-25
 */
public interface ISubScoreActionService extends IService<SubScoreAction> {

    Result<List<SubScoreActionVO>> getList(String subDimCode);

    Result<Void> updates(Integer id, UpdateSubScoreActionDTO updateSubScoreActionDTO);
}
