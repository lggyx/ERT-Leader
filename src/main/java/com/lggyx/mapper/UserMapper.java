package com.lggyx.mapper;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lggyx.pojo.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 测评用户档案 Mapper 接口
 * </p>
 *
 * @author lggyx
 * @since 2025-11-25
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
