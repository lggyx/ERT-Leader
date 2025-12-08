package com.lggyx.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lggyx.enumeration.SuccessCode;
import com.lggyx.dto.UpdatePortraitDTO;
import com.lggyx.entity.Portrait;
import com.lggyx.mapper.PortraitMapper;
import com.lggyx.vo.PortraitVO;
import com.lggyx.result.Result;
import com.lggyx.service.IPortraitService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
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
    @Resource
    private PortraitMapper portraitMapper;

    @Override
    public Result<List<PortraitVO>> getList() {
        List<Portrait> portraitList = portraitMapper.selectList(null);
        List<PortraitVO> portraitVOList = portraitList.stream().map(portrait -> {
            PortraitVO portraitVO = new PortraitVO();
            BeanUtils.copyProperties(portrait, portraitVO);
            return portraitVO;
        }).toList();
        return Result.success(SuccessCode.SUCCESS, portraitVOList);
    }

    @Override
    public Result<Void> updates(Integer id, UpdatePortraitDTO updatePortraitDTO) {
        Portrait portrait = new Portrait();
        BeanUtils.copyProperties(updatePortraitDTO, portrait);
        portrait.setId(id);
        int update = portraitMapper.update(portrait,
                Wrappers.<Portrait>lambdaQuery().eq(Portrait::getId, id));
        return update > 0 ? Result.success(SuccessCode.SUCCESS) : Result.error("更新失败");
    }
}
