package com.lggyx.service.impl;

import com.lggyx.pojo.dto.SubDimensionDTO;
import com.lggyx.pojo.entity.Dimension;
import com.lggyx.pojo.entity.SubDimension;
import com.lggyx.mapper.SubDimensionMapper;
import com.lggyx.pojo.vo.SubDimensionVO;
import com.lggyx.result.Result;
import com.lggyx.service.ISubDimensionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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

    @Override
    public Result<List<SubDimensionVO>> getList(String dimensionCode) {
        return null;
    }

    @Override
    public Result<Void> update(String code, SubDimensionDTO subDimensionDTO) {
        return null;
    }
}
