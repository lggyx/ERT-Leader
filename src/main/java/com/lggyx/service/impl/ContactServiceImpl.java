package com.lggyx.service.impl;

import com.lggyx.pojo.entity.Contact;
import com.lggyx.mapper.ContactMapper;
import com.lggyx.service.IContactService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统联系信息（仅一行） 服务实现类
 * </p>
 *
 * @author lggyx
 * @since 2025-11-25
 */
@Service
public class ContactServiceImpl extends ServiceImpl<ContactMapper, Contact> implements IContactService {

}
