package com.lggyx.service;

import com.lggyx.dto.DimensionDTO;
import com.lggyx.entity.Dimension;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lggyx.vo.DimensionVO;
import com.lggyx.result.Result;

import java.util.List;

/**
 * <p>
 * 三大能力维度主表 服务类
 * </p>
 *
 * @author lggyx
 * @since 2025-11-25
 */
public interface IDimensionService extends IService<Dimension> {

    Result<List<DimensionVO>> getList();

    Result<Void> updateDesc(String code, DimensionDTO dimensionDTO);
}
