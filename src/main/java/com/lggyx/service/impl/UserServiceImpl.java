package com.lggyx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lggyx.context.BaseContext;
import com.lggyx.enumeration.SuccessCode;
import com.lggyx.pojo.dto.LoginDTO;
import com.lggyx.pojo.dto.UserDTO;
import com.lggyx.pojo.entity.User;
import com.lggyx.mapper.UserMapper;
import com.lggyx.pojo.vo.CurrentUserVO;
import com.lggyx.pojo.vo.LoginVO;
import com.lggyx.pojo.vo.UserVO;
import com.lggyx.properties.JwtProperties;
import com.lggyx.result.PageResult;
import com.lggyx.result.Result;
import com.lggyx.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lggyx.utils.JwtUtil;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
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

    /**
     * 用户注册
     *
     * @param userDTO 用户信息
     * @return Result
     */
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
            return Result.success(SuccessCode.SUCCESS, userVO);
        else return Result.error("用户注册失败");
    }

    @Resource
    private JwtProperties jwtProperties;

    /**
     * 用户登录
     *
     * @param loginDTO 登录信息
     * @return Result
     */
    @Override
    public Result<LoginVO> login(@Valid LoginDTO loginDTO) {
        User user = userMapper.selectOne(Wrappers.<User>lambdaQuery()
                .eq(User::getMobile, loginDTO.getAccount())
                .or()
                .eq(User::getEmail, loginDTO.getAccount()));
        if (user == null)
            return Result.error("用户不存在");
        if (!user.getPassword().equals(loginDTO.getPassword()))
            return Result.error("密码错误");
        LoginVO loginVO = new LoginVO();
        JwtUtil jwtUtil = new JwtUtil(jwtProperties);
        loginVO.setToken(jwtUtil.generateToken(loginDTO.getAccount()));
        loginVO.setExpiresIn(String.valueOf(jwtProperties.getTtl()));
        LoginVO.UserInfo userInfo = new LoginVO.UserInfo();
        userInfo.setId(user.getId());
        userInfo.setName(user.getName());
        userInfo.setRole(user.getRole());
        loginVO.setUserInfo(userInfo);
        return Result.success(SuccessCode.SUCCESS, loginVO);
    }

    /**
     * 获取当前用户信息
     *
     * @return Result
     */
    @Override
    public Result<CurrentUserVO> getCurrentUser() {
        String account = BaseContext.getCurrentAccount();
        CurrentUserVO currentUserVO = new CurrentUserVO();
        User user = userMapper.selectOne(Wrappers.<User>lambdaQuery()
                .eq(User::getMobile, account)
                .or()
                .eq(User::getEmail, account));
        BeanUtils.copyProperties(user, currentUserVO);
        return Result.success(SuccessCode.SUCCESS, currentUserVO);
    }

    /**
     * 分页查询用户列表
     *
     * @param page     页码
     * @param pageSize 每页数量
     * @param status   状态：0 禁用，1 启用
     * @param role     角色：USER/INST_ADMIN/SUPER_ADMIN
     * @param keyword  关键字
     * @return Result
     */
    @Override
    public Result<PageResult> page(Integer page, Integer pageSize, String status, String role, String keyword) {
        Page<User> userPage = new Page<>(page, pageSize);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (status != null && !status.trim().isEmpty()) {
            queryWrapper.eq("status", status);
        }
        if (role != null && !role.trim().isEmpty()) {
            queryWrapper.eq("role", role);
        }
        if (keyword != null && !keyword.trim().isEmpty()) {
            queryWrapper.and(wrapper -> wrapper
                    .like("name", keyword)
                    .or()
                    .like("email", keyword)
                    .or()
                    .like("mobile", keyword));
        }
        IPage<User> resultPage = userMapper.selectPage(userPage, queryWrapper);
        PageResult pageResult = new PageResult();
        pageResult.setTotal(resultPage.getTotal());
        pageResult.setRecords(resultPage.getRecords());
        return Result.success(pageResult);
    }

    /**
     * 启用/禁用用户
     *
     * @param userId 用户ID
     * @param status 状态：0 禁用，1 启用
     * @return Result
     */
    @Override
    public Result<Void> updateStatus(Long userId, Integer status) {
        return null;
    }

    /**
     * 修改用户角色
     *
     * @param userId 用户ID
     * @param role   角色：USER/INST_ADMIN/SUPER_ADMIN
     * @return Result
     */
    @Override
    public Result<Void> updateRole(Long userId, String role) {
        return null;
    }


}
