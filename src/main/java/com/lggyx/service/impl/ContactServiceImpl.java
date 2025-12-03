package com.lggyx.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lggyx.enumeration.SuccessCode;
import com.lggyx.pojo.dto.ContactDTO;
import com.lggyx.pojo.entity.Contact;
import com.lggyx.mapper.ContactMapper;
import com.lggyx.pojo.vo.ContactVO;
import com.lggyx.result.Result;
import com.lggyx.service.IContactService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
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
    @Resource
    private ContactMapper contactMapper;

    @Override
    public Result<ContactVO> getContact() {
        Contact contact = contactMapper.selectById(1);
        ContactVO contactVO = new ContactVO();
        BeanUtils.copyProperties(contact, contactVO);
        return Result.success(SuccessCode.SUCCESS, contactVO);
    }

    @Override
    public Result<Void> updateContact(ContactDTO contactDTO) {
        Contact contact = new Contact();
        BeanUtils.copyProperties(contactDTO, contact);
        int update = contactMapper.update(
                contact,
                Wrappers.<Contact>lambdaQuery().eq(Contact::getId, 1)
        );
        return update > 0 ? Result.success() : Result.error("更新失败");
    }
}
