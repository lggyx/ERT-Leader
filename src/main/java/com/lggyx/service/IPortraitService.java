package com.lggyx.service;

import com.lggyx.pojo.dto.UpdatePortraitDTO;
import com.lggyx.pojo.entity.Portrait;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lggyx.pojo.vo.PortraitVO;
import com.lggyx.result.Result;

import java.util.List;

/**
 * <p>
 * 27 种领导力画像 服务类
 * </p>
 *
 * @author lggyx
 * @since 2025-11-25
 */
public interface IPortraitService extends IService<Portrait> {

    Result<List<PortraitVO>> getList();

    Result<Void> updates(Integer id, UpdatePortraitDTO updatePortraitDTO);
}
