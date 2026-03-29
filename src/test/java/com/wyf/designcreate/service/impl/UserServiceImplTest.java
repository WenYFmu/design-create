package com.wyf.designcreate.service.impl;

import com.wyf.designcreate.service.UserService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {
    @Resource
    private UserService userService;
    @Test
    void getUserById() {
        System.out.println(userService.getById(1));
    }
}