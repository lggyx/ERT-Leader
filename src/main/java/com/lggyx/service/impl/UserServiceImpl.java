package com.lggyx.service.impl;

import com.lggyx.pojo.dto.UserDTO;
import com.lggyx.pojo.entity.User;
import com.lggyx.mapper.UserMapper;
import com.lggyx.pojo.vo.UserVO;
import com.lggyx.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
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
    public UserVO add(UserDTO userDTO) {
        User user=new User();
        BeanUtils.copyProperties(userDTO,user);
        user.setId(null);//主键自增
        user.setCreatedAt(null);//默认创建时间
        user.setUpdatedAt(null);//默认更新时间
        user.setStatus(1);//默认启用
        user.setRole("USER");//默认普通用户
        int count=userMapper.insert(user);
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user,userVO);
        if (count>0)
            return userVO;
        else return null;
    }
}
