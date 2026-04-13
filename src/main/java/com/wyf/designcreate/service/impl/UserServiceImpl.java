package com.wyf.designcreate.service.impl;

import cn.hutool.core.util.DesensitizedUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wyf.designcreate.common.ErrorCode;
import com.wyf.designcreate.constant.UserConstant;
import com.wyf.designcreate.exception.BusinessException;
import com.wyf.designcreate.model.dto.user.RegisterRequest;
import com.wyf.designcreate.model.dto.user.UserQueryRequest;
import com.wyf.designcreate.model.entity.User;
import com.wyf.designcreate.model.enums.UserRoleEnum;
import com.wyf.designcreate.model.vo.UserVO;
import com.wyf.designcreate.service.UserService;
import com.wyf.designcreate.mapper.UserMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

/**
 * @author 15502
 * @description 针对表【user(用户)】的数据库操作Service实现
 * @createDate 2026-03-29 14:45:28
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Override
    public User login(String userAccount, String password, HttpServletRequest request) {
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号错误");
        }
        if (userAccount.length() > 256) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号错误");
        }
        if (password.length() < 6) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户密码错误");
        }
        if (password.length() > 256) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户密码错误");
        }
        String encryptedPasswords = getEncryptedPasswords(password);
        User user = lambdaQuery().eq(User::getUserAccount, userAccount)
                .eq(User::getUserPassword, encryptedPasswords)
                .one();
        if (user == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号或密码错误");
        }
        request.getSession().setAttribute(UserConstant.USER_LOGIN_STATUS, user);
        return user;
    }

    @Override
    public long register(RegisterRequest registerRequest) {
        if (registerRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = registerRequest.getUserAccount();
        String password = registerRequest.getPassword();
        String confirmPassword = registerRequest.getConfirmPassword();
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号过短");
        }
        if (userAccount.length() > 256) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号过长");
        }
        if (password.length() < 6) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户密码过短");
        }
        if (password.length() > 256) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户密码过长");
        }
        if (!password.equals(confirmPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "两次输入的密码不一致");
        }
        String encryptedPasswords = getEncryptedPasswords(password);
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptedPasswords);
        user.setUserRole(UserRoleEnum.USER.getValue());
        boolean res = this.save(user);
        if (!res) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "注册失败");
        }
        return user.getId();
    }

    @Override
    public boolean userLogout(HttpServletRequest request) {
        if (request.getSession().getAttribute(UserConstant.USER_LOGIN_STATUS) == null) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "未登录");
        }
        request.getSession().removeAttribute(UserConstant.USER_LOGIN_STATUS);
        return true;
    }

    @Override
    public Page<UserVO> getUserVOPage(UserQueryRequest userQueryRequest) {
        Long id = userQueryRequest.getId();
        String userAccount = userQueryRequest.getUserAccount();
        String userName = userQueryRequest.getUserName();
        String userAvatar = userQueryRequest.getUserAvatar();
        String userProfile = userQueryRequest.getUserProfile();
        String userRole = userQueryRequest.getUserRole();
        int current = userQueryRequest.getCurrent();
        int pageSize = userQueryRequest.getPageSize();
        String sortField = userQueryRequest.getSortField();
        String sortOrder = userQueryRequest.getSortOrder();

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(ObjUtil.isNotNull(id), "id", id)
                .eq(StrUtil.isNotBlank(userRole), "userRole", userRole)
                .like(StrUtil.isNotBlank(userAccount), "userAccount", userAccount)
                .like(StrUtil.isNotBlank(userName), "userName", userName)
                .like(StrUtil.isNotBlank(userProfile), "userProfile", userProfile)
                .orderBy(StrUtil.isNotBlank(sortField), sortOrder.equals("ascend"), sortField);
        Page<User> userPage = new Page<>(current, pageSize);
        userPage = this.baseMapper.selectPage(userPage, queryWrapper);
        //转换为UserVO
        Page<UserVO> userVOPage = new Page<>(current, pageSize, userPage.getTotal());
        userVOPage.setRecords(userPage.getRecords().stream().map(this::getUserVO).toList());

        return userVOPage;
    }


    @Override
    public User getLoginUser(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(UserConstant.USER_LOGIN_STATUS);
        if (user == null || user.getId() == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        /*
            避免已删除的用户仍能通过 Session 访问系统
            避免被禁用的账号仍能进行操作
            避免使用过期的用户信息进行业务处理
            避免数据不一致导致的各种 bug
         */
        user = this.getById(user.getId());
        if (user == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        return user;
    }


    @Override
    public UserVO getUserVO(User user) {
        if (user == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }

    @Override
    public boolean isAdmin(HttpServletRequest request) {
        User loginUser = getLoginUser(request);
        return loginUser != null && UserRoleEnum.ADMIN.getValue().equals(loginUser.getUserRole());
    }

    @Override
    public boolean isAdmin(User loginUser) {
        return loginUser != null && UserRoleEnum.ADMIN.getValue().equals(loginUser.getUserRole());
    }

    private String getEncryptedPasswords(String password) {
        return DigestUtils.md5DigestAsHex((password + UserConstant.SALT).getBytes());
    }
}




