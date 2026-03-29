package com.wyf.designcreate.annotation.myAuthCheck;

public @interface AuthCheck {
    String role() default "";
}
