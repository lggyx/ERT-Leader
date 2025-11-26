package com.lggyx.service;

import com.lggyx.pojo.dto.UserDTO;
import com.lggyx.pojo.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lggyx.pojo.vo.UserVO;

/**
 * <p>
 * 测评用户档案 服务类
 * </p>
 *
 * @author lggyx
 * @since 2025-11-25
 */
public interface IUserService extends IService<User> {

    UserVO add(UserDTO userDTO);
}
