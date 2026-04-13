package com.wyf.designcreate.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wyf.designcreate.annotation.myAuthCheck.Admin;
import com.wyf.designcreate.common.ErrorCode;
import com.wyf.designcreate.common.Result;
import com.wyf.designcreate.common.Results;
import com.wyf.designcreate.exception.BusinessException;
import com.wyf.designcreate.exception.ThrowUtil;
import com.wyf.designcreate.model.dto.user.LoginRequest;
import com.wyf.designcreate.model.dto.user.RegisterRequest;
import com.wyf.designcreate.model.dto.user.UserQueryRequest;
import com.wyf.designcreate.model.dto.user.UserUpdateRequest;
import com.wyf.designcreate.model.entity.User;
import com.wyf.designcreate.model.vo.UserVO;
import com.wyf.designcreate.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/login")
    public Result<User> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        ThrowUtil.throwIf(loginRequest == null, new BusinessException(ErrorCode.PARAMS_ERROR));
        String userAccount = loginRequest.getUserAccount();
        String password = loginRequest.getPassword();
        ThrowUtil.throwIf(StrUtil.hasBlank(userAccount, password), new BusinessException(ErrorCode.PARAMS_ERROR));
        User loginUser = userService.login(userAccount, password, request);
        return Results.success(loginUser);
    }

    @PostMapping("/register")
    public Result<Long> register(@RequestBody RegisterRequest registerRequest) {
        ThrowUtil.throwIf(registerRequest == null, new BusinessException(ErrorCode.PARAMS_ERROR));
        Long id = userService.register(registerRequest);
        return Results.success(id);
    }

    @GetMapping("/logout")
    public Result<Boolean> logout(HttpServletRequest request) {
        boolean res = userService.userLogout(request);
        return Results.success(res);
    }

    @GetMapping("/get/login")
    public Result<User> getLoginUser(HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        return Results.success(loginUser);
    }

    @PostMapping("/update")
    public Result<Boolean> updateUser(@RequestBody UserUpdateRequest userUpdateRequest, HttpServletRequest request) {
        ThrowUtil.throwIf(userUpdateRequest == null, new BusinessException(ErrorCode.PARAMS_ERROR));
        User loginUser = userService.getLoginUser(request);
        //未登录
        ThrowUtil.throwIf(loginUser == null, new BusinessException(ErrorCode.NOT_LOGIN));
        ThrowUtil.throwIf(!loginUser.getId().equals(userUpdateRequest.getId()) && !userService.isAdmin(request), new BusinessException(ErrorCode.NO_AUTH));
        User user = new User();
        BeanUtil.copyProperties(userUpdateRequest, user);
        boolean res = userService.updateById(user);
        return Results.success(res);
    }

    @PostMapping("/delete")
    public Result<Boolean> deleteUser(Long id, HttpServletRequest request) {
        ThrowUtil.throwIf(id == null || id < 1, new BusinessException(ErrorCode.PARAMS_ERROR));
        User loginUser = userService.getLoginUser(request);
        //未登录
        ThrowUtil.throwIf(loginUser == null, new BusinessException(ErrorCode.NOT_LOGIN));
        //本人和管理员可删除
        ThrowUtil.throwIf(!userService.isAdmin(request), new BusinessException(ErrorCode.NO_AUTH));

        boolean res = userService.removeById(id);
        return Results.success(res);
    }

    @PostMapping("/get/list")
    @Admin
    public Result<Page<UserVO>> getUserList(@RequestBody UserQueryRequest userQueryRequest) {
        ThrowUtil.throwIf(userQueryRequest == null, new BusinessException(ErrorCode.PARAMS_ERROR));
        return Results.success(userService.getUserVOPage(userQueryRequest));
    }
    @GetMapping("/get")
    @Admin
    public Result<User> getUserById(@RequestParam Long id) {
        ThrowUtil.throwIf(id == null || id < 1, new BusinessException(ErrorCode.PARAMS_ERROR));
        User user = userService.getById(id);
        ThrowUtil.throwIf(user == null, new BusinessException(ErrorCode.NULL_ERROR, "用户信息不存在"));
        return Results.success(user);
    }

    @GetMapping("/get/vo")
    public Result<UserVO> getUserVOById(@RequestParam Long id) {
        ThrowUtil.throwIf(id == null || id < 1, new BusinessException(ErrorCode.PARAMS_ERROR));
        User user = userService.getById(id);
        ThrowUtil.throwIf(user == null, new BusinessException(ErrorCode.NULL_ERROR, "用户信息不存在"));
        return Results.success(userService.getUserVO(user));
    }
}
