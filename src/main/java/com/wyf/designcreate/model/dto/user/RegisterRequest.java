package com.wyf.designcreate.model.dto.user;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class RegisterRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -7330159160946972780L;
    /**
     * 用户账号
     */
    String userAccount;
    /**
     * 密码
     */
    String password;
    /**
     * 确认密码
     */
    String confirmPassword;
}
