package com.lggyx.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lggyx.enumeration.SuccessCode;
import com.lggyx.pojo.dto.UpdateErtScoreDescDTO;
import com.lggyx.pojo.entity.ErtScoreDesc;
import com.lggyx.mapper.ErtScoreDescMapper;
import com.lggyx.pojo.vo.ErtScoreDescVO;
import com.lggyx.result.Result;
import com.lggyx.service.IErtScoreDescService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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
@Slf4j
public class ErtScoreDescServiceImpl extends ServiceImpl<ErtScoreDescMapper, ErtScoreDesc> implements IErtScoreDescService {
    @Resource
    private ErtScoreDescMapper ertScoreDescMapper;

    /**
     * 查询得分区间
     *
     * @return 得分区间
     */
    @Override
    public Result<List<ErtScoreDescVO>> getList() {
        List<ErtScoreDesc> ertScoreDescList = ertScoreDescMapper.selectList(null);
        log.info("查询得分区间:{}", ertScoreDescList);
        List<ErtScoreDescVO> ertScoreDescVOList = ertScoreDescList.stream().map(ertScoreDesc -> {
            ErtScoreDescVO ertScoreDescVO = new ErtScoreDescVO();
            BeanUtils.copyProperties(ertScoreDesc, ertScoreDescVO);
            return ertScoreDescVO;
        }).toList();
        return Result.success(SuccessCode.SUCCESS, ertScoreDescVOList);
    }

    /**
     * 更新得分区间描述
     *
     * @param id                    主键
     * @param updateErtScoreDescDTO 更新得分区间描述
     * @return  Result
     */
    @Override
    public Result<Void> updates(Integer id, UpdateErtScoreDescDTO updateErtScoreDescDTO) {
        ErtScoreDesc ertScoreDesc = new ErtScoreDesc();
        BeanUtils.copyProperties(updateErtScoreDescDTO, ertScoreDesc);
        ertScoreDesc.setId(id);
        int update = ertScoreDescMapper.update(
                ertScoreDesc,
                Wrappers.<ErtScoreDesc>lambdaQuery().eq(ErtScoreDesc::getId, id)
        );
        return update > 0 ? Result.success(SuccessCode.SUCCESS) : Result.error("更新得分区间描述失败");
    }
}
