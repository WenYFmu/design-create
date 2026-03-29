package com.wyf.designcreate.service;

import com.wyf.designcreate.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletRequest;

/**
* @author 15502
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2026-03-29 14:45:28
*/
public interface UserService extends IService<User> {

    User getLoginUser(HttpServletRequest request);
}
