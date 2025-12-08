package com.lggyx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lggyx.dto.SubDimensionDTO;
import com.lggyx.entity.SubDimension;
import com.lggyx.mapper.SubDimensionMapper;
import com.lggyx.vo.SubDimensionVO;
import com.lggyx.result.Result;
import com.lggyx.service.ISubDimensionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 12 项子能力配置 服务实现类
 * </p>
 *
 * @author lggyx
 * @since 2025-11-25
 */
@Service
public class SubDimensionServiceImpl extends ServiceImpl<SubDimensionMapper, SubDimension> implements ISubDimensionService {
    @Resource
    private SubDimensionMapper subDimensionMapper;

    @Override
    public Result<List<SubDimensionVO>> getList(String dimensionCode) {
        List<SubDimension> subDimensionList = subDimensionMapper.selectList(
                new QueryWrapper<SubDimension>().eq("dimension_code", dimensionCode));
        List<SubDimensionVO> subDimensionVOList = subDimensionList.stream().map(subDimension -> {
            SubDimensionVO subDimensionVO = new SubDimensionVO();
            BeanUtils.copyProperties(subDimension, subDimensionVO);
            return subDimensionVO;
        }).toList();
        return Result.success(subDimensionVOList);
    }

    @Override
    public Result<Void> update(String code, SubDimensionDTO subDimensionDTO) {
        SubDimension subDimension = new SubDimension();
        BeanUtils.copyProperties(subDimensionDTO, subDimension);
        int update = subDimensionMapper.update(subDimension,
                new QueryWrapper<SubDimension>().eq("dimension_code", code));
        return update > 0 ? Result.success() : Result.error("更新失败");
    }
}
