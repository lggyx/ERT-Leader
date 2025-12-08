package com.lggyx.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lggyx.enumeration.SuccessCode;
import com.lggyx.dto.UpdateSubScoreActionDTO;
import com.lggyx.entity.SubScoreAction;
import com.lggyx.mapper.SubScoreActionMapper;
import com.lggyx.vo.SubScoreActionVO;
import com.lggyx.result.Result;
import com.lggyx.service.ISubScoreActionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

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
    @Resource
    private SubScoreActionMapper subScoreActionMapper;

    /**
     * 查询子维度得分行动方案
     *
     * @param subDimCode 子维度编码（非必须 ）
     * @return 子维度得分行动方案
     */
    @Override
    public Result<List<SubScoreActionVO>> getList(String subDimCode) {
        List<SubScoreAction> subScoreActionList = subScoreActionMapper.selectList(
                Wrappers.<SubScoreAction>lambdaQuery().eq(SubScoreAction::getSubDimCode, subDimCode)
        );
        List<SubScoreActionVO> subScoreActionVOList = subScoreActionList.stream().map(subScoreAction -> {
            SubScoreActionVO subScoreActionVO = new SubScoreActionVO();
            BeanUtils.copyProperties(subScoreAction, subScoreActionVO);
            return subScoreActionVO;
        }).toList();
        return Result.success(SuccessCode.SUCCESS, subScoreActionVOList);
    }

    /**
     * 更新子维度得分行动方案
     *
     * @param id                      主键
     * @param updateSubScoreActionDTO 更新子维度得分行动方案DTO
     * @return Result
     */
    @Override
    public Result<Void> updates(Integer id, UpdateSubScoreActionDTO updateSubScoreActionDTO) {
        SubScoreAction subScoreAction = new SubScoreAction();
        BeanUtils.copyProperties(updateSubScoreActionDTO, subScoreAction);
        subScoreAction.setId(id);
        int update = subScoreActionMapper.update(
                subScoreAction,
                Wrappers.<SubScoreAction>lambdaQuery().eq(SubScoreAction::getId, id)
        );
        return update > 0 ? Result.success(SuccessCode.SUCCESS) : Result.error("更新子维度得分行动方案失败");
    }
}
