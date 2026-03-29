package com.wyf.designcreate.aop;

import com.wyf.designcreate.annotation.myAuthCheck.Admin;
import com.wyf.designcreate.annotation.myAuthCheck.AuthCheck;
import com.wyf.designcreate.common.ErrorCode;
import com.wyf.designcreate.exception.BusinessException;
import com.wyf.designcreate.model.entity.User;
import com.wyf.designcreate.model.enums.UserRoleEnum;
import com.wyf.designcreate.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class AuthInterceptor {

    @Resource
    private UserService userService;

    /**
     * 执行拦截
     *
     * @param joinPoint 切入点
     * @param authCheck 权限校验注解
     */
    @Around("@annotation(authCheck)")
    public Object doInterceptor(ProceedingJoinPoint joinPoint, AuthCheck authCheck) throws Throwable {
        String role = authCheck.role();
        //无需权限放行
        if (role == null || role.isEmpty()) {
            return joinPoint.proceed();
        }
        //获取当前用户权限
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();//这里不能用方法注入，构造器注入比较麻烦
        User loginUser = userService.getLoginUser(request);
        UserRoleEnum userRole = UserRoleEnum.getUserEnum(loginUser.getUserRole());
        //无权限
        if (userRole == null) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        //需要管理员权限，但无管理员权限
        if (UserRoleEnum.ADMIN.getValue().equals(role) && !UserRoleEnum.ADMIN.equals(userRole)){
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        return joinPoint.proceed();//执行方法
    }

    /**
     * 执行拦截 - @Admin 专用
     *
     * @param joinPoint 切入点
     * @param admin 管理员权限注解
     */
    @Around("@annotation(admin)")
    public Object doAdminInterceptor(ProceedingJoinPoint joinPoint, Admin admin) throws Throwable {
        //获取当前用户权限
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        User loginUser = userService.getLoginUser(request);
        UserRoleEnum userRole = UserRoleEnum.getUserEnum(loginUser.getUserRole());
        
        //无权限
        if (userRole == null) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        
        //必须是管理员
        if (!UserRoleEnum.ADMIN.equals(userRole)) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        
        return joinPoint.proceed();//执行方法
    }
}
