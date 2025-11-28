package com.lggyx.service.impl;

import com.lggyx.pojo.dto.UpdateSubScoreActionDTO;
import com.lggyx.pojo.entity.SubScoreAction;
import com.lggyx.mapper.SubScoreActionMapper;
import com.lggyx.pojo.vo.SubScoreActionVO;
import com.lggyx.result.Result;
import com.lggyx.service.ISubScoreActionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 子维度得分行动方案 服务实现类
 * </p>
 *
 * @author lggyx
 * @since 2025-11-25
 */
@Service
public class SubScoreActionServiceImpl extends ServiceImpl<SubScoreActionMapper, SubScoreAction> implements ISubScoreActionService {
    /**
     * 查询子维度得分行动方案
     * @param subDimCode 子维度编码（非必须 ）
     * @return
     */
    @Override
    public Result<SubScoreActionVO> getList(String subDimCode) {
        return null;
    }
    /**
     * 更新子维度得分行动方案
     * @param id 主键
     * @param updateSubScoreActionDTO 更新子维度得分行动方案DTO
     * @return
     */
    @Override
    public Result<Void> updates(Integer id, UpdateSubScoreActionDTO updateSubScoreActionDTO) {
        return null;
    }
}
