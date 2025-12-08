package com.lggyx.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lggyx.enumeration.SuccessCode;
import com.lggyx.pojo.dto.DimensionDTO;
import com.lggyx.pojo.entity.Dimension;
import com.lggyx.mapper.DimensionMapper;
import com.lggyx.pojo.vo.DimensionVO;
import com.lggyx.result.Result;
import com.lggyx.service.IDimensionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 三大能力维度主表 服务实现类
 * </p>
 *
 * @author lggyx
 * @since 2025-11-25
 */
@Service
public class DimensionServiceImpl extends ServiceImpl<DimensionMapper, Dimension> implements IDimensionService {
    @Resource
    private DimensionMapper dimensionMapper;
    /**
     * 查询三大能力维度
     *
     * @return 三大能力维度
     */
    @Override
    public Result<List<DimensionVO>> getList() {
        List<Dimension> dimensionList = dimensionMapper.selectList( null);
        List<DimensionVO> dimensionVOList = dimensionList.stream().map(dimension -> {
            DimensionVO dimensionVO = new DimensionVO();
            BeanUtils.copyProperties(dimension, dimensionVO);
            return dimensionVO;
        }).toList();
        return Result.success(dimensionVOList);

    }
    /**
     * 更新维度描述
     *
     * @param code 维度编码(E/R/T)
     * @param dimensionDTO 维度描述
     * @return  Result
     */
    @Override
    public Result<Void> updateDesc(String code, DimensionDTO dimensionDTO) {
        Dimension dimension = new Dimension();
        BeanUtils.copyProperties(dimensionDTO, dimension);
        dimension.setCode(code);
        int update = dimensionMapper.update(
                dimension,
                Wrappers.<Dimension>lambdaQuery().eq(Dimension::getCode, code)
        );
        return update > 0 ? Result.success(SuccessCode.SUCCESS) : Result.error("更新维度描述失败");
    }

}
