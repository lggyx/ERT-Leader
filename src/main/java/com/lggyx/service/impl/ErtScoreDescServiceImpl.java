package com.lggyx.service.impl;

import com.lggyx.pojo.dto.UpdateErtScoreDescDTO;
import com.lggyx.pojo.entity.ErtScoreDesc;
import com.lggyx.mapper.ErtScoreDescMapper;
import com.lggyx.pojo.vo.ErtScoreDescVO;
import com.lggyx.result.Result;
import com.lggyx.service.IErtScoreDescService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 维度得分区间解释 服务实现类
 * </p>
 *
 * @author lggyx
 * @since 2025-11-25
 */
@Service
public class ErtScoreDescServiceImpl extends ServiceImpl<ErtScoreDescMapper, ErtScoreDesc> implements IErtScoreDescService {
    /**
     * 查询得分区间
     * @return
     */
    @Override
    public Result<List<ErtScoreDescVO>> getList() {
        return null;
    }
    /**
     * 更新得分区间描述
     * @param id 主键
     * @param updateErtScoreDescDTO 更新得分区间描述
     * @return
     */
    @Override
    public Result<Void> updates(Integer id, UpdateErtScoreDescDTO updateErtScoreDescDTO) {
        return null;
    }
}
