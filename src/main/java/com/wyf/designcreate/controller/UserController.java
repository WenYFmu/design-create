package com.wyf.designcreate.controller;

import com.wyf.designcreate.annotation.myAuthCheck.Admin;
import com.wyf.designcreate.common.ErrorCode;
import com.wyf.designcreate.common.Result;
import com.wyf.designcreate.common.Results;
import com.wyf.designcreate.exception.BusinessException;
import com.wyf.designcreate.exception.ThrowUtil;
import com.wyf.designcreate.model.entity.User;
import com.wyf.designcreate.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/get")
    @Admin
    public Result<User> getUserById(@RequestParam Long id) {
        ThrowUtil.throwIf(id == null || id < 1, new BusinessException(ErrorCode.PARAMS_ERROR));
        User user = userService.getById(id);
        ThrowUtil.throwIf(user == null, new BusinessException(ErrorCode.NULL_ERROR, "用户信息不存在"));
        return Results.success(user);
    }
}
