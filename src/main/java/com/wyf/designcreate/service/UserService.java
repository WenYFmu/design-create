package com.wyf.designcreate.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wyf.designcreate.model.dto.user.RegisterRequest;
import com.wyf.designcreate.model.dto.user.UserQueryRequest;
import com.wyf.designcreate.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wyf.designcreate.model.vo.UserVO;
import jakarta.servlet.http.HttpServletRequest;

/**
* @author 15502
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2026-03-29 14:45:28
*/
public interface UserService extends IService<User> {

    /**
     * 用户登录
     * @param userAccount
     * @param password
     * @return
     */
    User login(String userAccount, String password, HttpServletRequest request);

    /**
     * 用户注册
     * @param registerRequest 注册信息
     * @return 用户id
     */
    long register(RegisterRequest registerRequest);

    /**
     * 用户注销
     * @param request
     * @return
     */
    boolean userLogout(HttpServletRequest request);

    /**
     * 获取用户分页
     * @param userQueryRequest 查询条件
     * @return
     */
    Page<UserVO> getUserVOPage(UserQueryRequest userQueryRequest);

    /**
     * 获取当前登录用户
     */
    User getLoginUser(HttpServletRequest request);

    UserVO getUserVO(User user);

    boolean isAdmin(HttpServletRequest request);
}
