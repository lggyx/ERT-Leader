package com.lggyx.service.impl;

import com.lggyx.pojo.dto.DimensionDTO;
import com.lggyx.pojo.entity.Dimension;
import com.lggyx.mapper.DimensionMapper;
import com.lggyx.pojo.vo.DimensionVO;
import com.lggyx.result.Result;
import com.lggyx.service.IDimensionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
    /**
     * 查询三大能力维度
     *
     * @return
     */
    @Override
    public Result<List<DimensionVO>> getList() {
        return null;
    }
    /**
     * 更新维度描述
     *
     * @param code 维度编码(E/R/T)
     * @param dimensionDTO 维度描述
     * @return
     */
    @Override
    public Result<Void> updateDesc(String code, DimensionDTO dimensionDTO) {
        return null;
    }

}
