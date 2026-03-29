package com.wyf.designcreate.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wyf.designcreate.common.ErrorCode;
import com.wyf.designcreate.constant.UserConstant;
import com.wyf.designcreate.exception.BusinessException;
import com.wyf.designcreate.model.entity.User;
import com.wyf.designcreate.model.vo.UserVO;
import com.wyf.designcreate.service.UserService;
import com.wyf.designcreate.mapper.UserMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

/**
* @author 15502
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2026-03-29 14:45:28
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Override
    public User getLoginUser(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(UserConstant.USER_LOGIN_STATUS);
        if(user == null || user.getId() == null){
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        /*
            避免已删除的用户仍能通过 Session 访问系统
            避免被禁用的账号仍能进行操作
            避免使用过期的用户信息进行业务处理
            避免数据不一致导致的各种 bug
         */
        user = this.getById(user.getId());
        if(user == null){
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        return user;
    }
}




