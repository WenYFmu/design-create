package com.wyf.designcreate.service;

import com.wyf.designcreate.model.dto.app.AddAppRequest;
import com.wyf.designcreate.model.entity.App;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wyf.designcreate.model.entity.User;

/**
 * @author 15502
 * @description 针对表【app(应用)】的数据库操作Service
 * @createDate 2026-03-31 21:52:29
 */
public interface AppService extends IService<App> {

    /**
     * 创建应用 并生成应用名称
     *
     * @param addAppRequest
     * @return
     */
    Long addApp(AddAppRequest addAppRequest, User user);
}
