package com.wyf.designcreate.controller;

import com.wyf.designcreate.common.ErrorCode;
import com.wyf.designcreate.common.Result;
import com.wyf.designcreate.common.Results;
import com.wyf.designcreate.exception.BusinessException;
import com.wyf.designcreate.model.dto.app.AddAppRequest;
import com.wyf.designcreate.model.dto.app.UpdateAppRequest;
import com.wyf.designcreate.model.entity.User;
import com.wyf.designcreate.service.AppService;
import com.wyf.designcreate.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app")
public class AppController {

    @Resource
    private AppService appService;

    @Resource
    private UserService userService;
    @PostMapping("/add")
    public Result<Long> addApp(@RequestBody AddAppRequest addAppRequest, HttpServletRequest request) {
        if (addAppRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数错误");
        }
        User loginUser = userService.getLoginUser(request);
        Long appId = appService.addApp(addAppRequest, loginUser);
        return Results.success(appId);
    }

    @PostMapping("/update")
    public String updateApp(@RequestBody UpdateAppRequest updateAppRequest) {
        return "更新应用成功";
    }

    @PostMapping("/delete")
    public String deleteApp() {
        return "删除应用成功";
    }

    @PostMapping("/list")
    public String listApp() {
        return "获取应用列表成功";
    }


}
