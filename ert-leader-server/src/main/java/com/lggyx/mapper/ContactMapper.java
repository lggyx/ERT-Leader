package com.lggyx.mapper;

import com.lggyx.entity.Contact;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 系统联系信息（仅一行） Mapper 接口
 * </p>
 *
 * @author lggyx
 * @since 2025-11-25
 */
@Mapper
public interface ContactMapper extends BaseMapper<Contact> {

}
