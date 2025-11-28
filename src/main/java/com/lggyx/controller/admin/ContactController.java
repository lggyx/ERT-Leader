package com.lggyx.controller.admin;

import com.lggyx.pojo.dto.ContactDTO;
import com.lggyx.pojo.entity.Contact;
import com.lggyx.pojo.vo.ContactVO;
import com.lggyx.result.Result;
import com.lggyx.service.IContactService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "系统配置管理", description = "联系信息管理")
@RestController
public class ContactController {
    @Resource
    private IContactService contactService;

    @Operation(summary = "获取联系信息")
    @GetMapping("/api/admin/contact")
    public Result<ContactVO> getContact() {
        return contactService.getContact();
    }
    @Operation(summary = "更新联系信息")
    @PutMapping("/api/admin/contact")
    public Result<Void> updateContact(
            @RequestBody ContactDTO contactDTO) {
        return contactService.updateContact(contactDTO);
    }

}
