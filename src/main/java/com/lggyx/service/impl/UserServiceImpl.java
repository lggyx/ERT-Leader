package com.lggyx.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lggyx.pojo.dto.LoginDTO;
import com.lggyx.pojo.dto.UserDTO;
import com.lggyx.pojo.entity.User;
import com.lggyx.mapper.UserMapper;
import com.lggyx.pojo.vo.LoginVO;
import com.lggyx.pojo.vo.UserVO;
import com.lggyx.result.Result;
import com.lggyx.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lggyx.utils.JwtUtils;
import com.lggyx.utils.ValidatorUtils;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 测评用户档案 服务实现类
 * </p>
 *
 * @author lggyx
 * @since 2025-11-25
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Resource
    public UserMapper userMapper;

    @Override
    public Result<UserVO> add(UserDTO userDTO) {
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        user.setId(null);//主键自增
        user.setCreatedAt(null);//默认创建时间
        user.setUpdatedAt(null);//默认更新时间
        user.setStatus(1);//默认启用
        user.setRole("USER");//默认普通用户
        int count = userMapper.insert(user);
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        if (count > 0)
            return Result.success(userVO);
        else return Result.error("用户注册失败");
    }




    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private JwtUtils jwtUtils;
    @Override
    public Result<LoginVO> login(@Valid LoginDTO userDTO) {
        //判断用邮箱还是手机号登录
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDTO.getAccount(), userDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(auth);
        String token = jwtUtils.generateToken(userDTO.getAccount());
        LoginVO loginVO = new LoginVO();
        loginVO.setToken(token);
        loginVO.setExpiresIn(String.valueOf(jwtUtils.expiration));
        User user = null;
        if (ValidatorUtils.isMobile(userDTO.getAccount())) {          // 正则判断 1\d{10}
            user = findByMobile(userDTO.getAccount());
        } else if (ValidatorUtils.isEmail(userDTO.getAccount())) {    // 正则判断邮箱
            user =findByEmail(userDTO.getAccount());
        }
        LoginVO.UserInfo userInfo = new LoginVO.UserInfo();
        if (user != null) {
            userInfo.setId(user.getId());
            userInfo.setName(user.getName());
            userInfo.setRole(user.getRole());
        }
        loginVO.setUserInfo(userInfo);
        return Result.success(loginVO);
    }

    @Override
    public UserDetails loadUserByLoginKey(String loginKey) {
        User user = null;
        if (ValidatorUtils.isMobile(loginKey)) {          // 正则判断 1\d{10}
            user = findByMobile(loginKey);
        } else if (ValidatorUtils.isEmail(loginKey)) {    // 正则判断邮箱
            user =findByEmail(loginKey);
        }
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        return org.springframework.security.core.userdetails.User.builder()
                .username(loginKey)          // 用 loginKey 充当 username
                .password(user.getPassword())
                .authorities("ROLE_USER")
                .build();
    }

    // 根据手机号查
    User findByMobile(String mobile) {
        return userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getMobile, mobile));
    }

    // 根据邮箱查
    User findByEmail(String email) {
        return userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getEmail, email));
    }
}
