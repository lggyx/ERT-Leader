package com.lggyx.service.impl;

import com.lggyx.pojo.dto.UpdatePortraitDTO;
import com.lggyx.pojo.entity.Portrait;
import com.lggyx.mapper.PortraitMapper;
import com.lggyx.pojo.vo.PortraitVO;
import com.lggyx.result.Result;
import com.lggyx.service.IPortraitService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 27 种领导力画像 服务实现类
 * </p>
 *
 * @author lggyx
 * @since 2025-11-25
 */
@Service
public class PortraitServiceImpl extends ServiceImpl<PortraitMapper, Portrait> implements IPortraitService {

    @Override
    public Result<List<PortraitVO>> getList() {
        return null;
    }

    @Override
    public Result<Void> updates(Integer id, UpdatePortraitDTO updatePortraitDTO) {
        return null;
    }
}
