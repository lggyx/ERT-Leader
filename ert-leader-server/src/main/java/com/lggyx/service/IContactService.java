package com.lggyx.service;

import com.lggyx.dto.ContactDTO;
import com.lggyx.entity.Contact;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lggyx.vo.ContactVO;
import com.lggyx.result.Result;

/**
 * <p>
 * 系统联系信息（仅一行） 服务类
 * </p>
 *
 * @author lggyx
 * @since 2025-11-25
 */
public interface IContactService extends IService<Contact> {

    Result<ContactVO> getContact();

    Result<Void> updateContact(ContactDTO contactDTO);
}
