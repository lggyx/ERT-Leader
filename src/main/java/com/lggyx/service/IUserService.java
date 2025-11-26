package com.lggyx.service;

import com.lggyx.pojo.dto.LoginDTO;
import com.lggyx.pojo.dto.UserDTO;
import com.lggyx.pojo.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lggyx.pojo.vo.LoginVO;
import com.lggyx.pojo.vo.UserVO;
import com.lggyx.result.Result;
import jakarta.validation.Valid;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * <p>
 * 测评用户档案 服务类
 * </p>
 *
 * @author lggyx
 * @since 2025-11-25
 */
public interface IUserService extends IService<User> {

    Result<UserVO> add(UserDTO userDTO);

    Result<LoginVO> login(@Valid LoginDTO userDTO);

    UserDetails loadUserByLoginKey(String username);
}
