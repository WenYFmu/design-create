package com.wyf.designcreate.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wyf.designcreate.model.dto.app.AddAppRequest;
import com.wyf.designcreate.model.dto.app.QueueAppRequest;
import com.wyf.designcreate.model.dto.app.UpdateAppRequest;
import com.wyf.designcreate.model.entity.App;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wyf.designcreate.model.entity.User;
import com.wyf.designcreate.model.vo.AppVO;
import reactor.core.publisher.Flux;

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


    /**
     * 更新应用
     *
     * @param updateAppRequest
     * @param loginUser
     * @return
     */
    boolean updateApp(UpdateAppRequest updateAppRequest, User loginUser);

    /**
     * 删除应用
     *
     * @param appId
     * @param loginUser
     * @return
     */
    boolean deleteApp(Long appId, User loginUser);

    /**
     * 获取应用VO
     *
     * @param app
     * @return
     */
    AppVO getAppVO(App app);

    /**
     * 获取应用列表VO
     *
     * @param appPage
     * @return
     */
    Page<AppVO> getPageVO(Page<App> appPage);

    /**
     * 获取应用列表查询条件
     * @param queueAppRequest
     * @return
     */
    QueryWrapper<App> getQueueWrapper(QueueAppRequest queueAppRequest);

    /**
     * 部署应用
     *
     * @param app
     * @return 应用访问链接
     */
    String deploy(App app);

    /**
     * 应用聊天生成代码（流式 SSE）
     *
     * @param appId   应用 ID
     * @param message 用户消息
     * @param loginUser 登录用户
     * @return 聊天生成代码结果流
     */
    Flux<String> chatToGenCode(Long appId, String message, User loginUser);
}
