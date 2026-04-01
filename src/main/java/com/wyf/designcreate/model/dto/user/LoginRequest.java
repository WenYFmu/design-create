package com.wyf.designcreate.model.dto.user;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class LoginRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = -5109746047759318365L;
    /**
     * 用户账号
     */
    String userAccount;

    /**
     * 用户密码
     */
    String password;
}
