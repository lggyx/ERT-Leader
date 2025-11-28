package com.lggyx.service;

import com.lggyx.pojo.dto.UpdateErtScoreDescDTO;
import com.lggyx.pojo.entity.ErtScoreDesc;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lggyx.pojo.vo.ErtScoreDescVO;
import com.lggyx.result.Result;

import java.util.List;

/**
 * <p>
 * 维度得分区间解释 服务类
 * </p>
 *
 * @author lggyx
 * @since 2025-11-25
 */
public interface IErtScoreDescService extends IService<ErtScoreDesc> {

    Result<List<ErtScoreDescVO>> getList();

    Result<Void> updates(Integer id, UpdateErtScoreDescDTO updateErtScoreDescDTO);
}
