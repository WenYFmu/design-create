package com.wyf.designcreate.annotation.myAuthCheck;

import com.wyf.designcreate.constant.UserConstant;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@AuthCheck(role = UserConstant.ADMIN)
public @interface Admin {

}
