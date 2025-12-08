package com.lggyx.service;

import com.lggyx.dto.SubDimensionDTO;
import com.lggyx.entity.Dimension;
import com.lggyx.entity.SubDimension;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lggyx.vo.SubDimensionVO;
import com.lggyx.result.Result;

import java.util.List;

/**
 * <p>
 * 12 项子能力配置 服务类
 * </p>
 *
 * @author lggyx
 * @since 2025-11-25
 */
public interface ISubDimensionService extends IService<SubDimension> {

    Result<List<SubDimensionVO>> getList(String dimensionCode);

    Result<Void> update(String code, SubDimensionDTO subDimensionDTO);
}
