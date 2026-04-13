package com.wyf.designcreate.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wyf.designcreate.ai.message.service.MessageService;
import com.wyf.designcreate.annotation.myAuthCheck.Admin;
import com.wyf.designcreate.common.ErrorCode;
import com.wyf.designcreate.common.Result;
import com.wyf.designcreate.common.Results;
import com.wyf.designcreate.constant.AppConstant;
import com.wyf.designcreate.exception.BusinessException;
import com.wyf.designcreate.exception.ThrowUtil;
import com.wyf.designcreate.model.dto.app.AddAppRequest;
import com.wyf.designcreate.model.dto.app.ChatHistoryQueryRequest;
import com.wyf.designcreate.model.dto.app.QueueAppRequest;
import com.wyf.designcreate.model.dto.app.UpdateAppRequest;
import com.wyf.designcreate.model.entity.App;
import com.wyf.designcreate.model.entity.Message;
import com.wyf.designcreate.model.entity.User;
import com.wyf.designcreate.model.vo.AppVO;
import com.wyf.designcreate.service.AppService;
import com.wyf.designcreate.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/app")
public class AppController {

    @Resource
    private AppService appService;

    @Resource
    private UserService userService;

    @Resource
    private MessageService messageService;

    @PostMapping("/add")
    public Result<Long> addApp(@RequestBody AddAppRequest addAppRequest, HttpServletRequest request) {
        if (addAppRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数错误");
        }
        User loginUser = userService.getLoginUser(request);
        Long appId = appService.addApp(addAppRequest, loginUser);
        return Results.success(appId);
    }

    @GetMapping("/get/vo")
    public Result<AppVO> getAppVO(@RequestParam Long appId, HttpServletRequest request) {
        if (appId == null || appId < 1) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数错误");
        }
        //todo 鉴权：公开/私有
        App app = appService.getById(appId);
        if (app == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "应用不存在");
        }
        return Results.success(appService.getAppVO(app));
    }

    @GetMapping("/get")
    @Admin
    public Result<App> getApp(@RequestParam Long appId, HttpServletRequest request) {
        if (appId == null || appId < 1) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数错误");
        }
        App app = appService.getById(appId);
        if (app == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "应用不存在");
        }
        return Results.success(app);
    }

    @PostMapping("/update")
    public Result<Boolean> updateApp(@RequestBody UpdateAppRequest updateAppRequest, HttpServletRequest request) {
        if (updateAppRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数错误");
        }
        User loginUser = userService.getLoginUser(request);
        boolean res = appService.updateApp(updateAppRequest, loginUser);
        return Results.success(res);
    }

    @PostMapping("/delete")
    public Result<Boolean> deleteApp(Long appId, HttpServletRequest request) {
        if (appId == null || appId < 1) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数错误");
        }
        User loginUser = userService.getLoginUser(request);
        boolean res = appService.deleteApp(appId, loginUser);
        return Results.success(res);
    }

    @PostMapping("/my/list/page/vo")
    public Result<Page<AppVO>> myListPageVO(@RequestBody QueueAppRequest queueAppRequest, HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        //查询自己的应用
        queueAppRequest.setUserId(loginUser.getId());
        QueryWrapper<App> queueWrapper = appService.getQueueWrapper(queueAppRequest);
        Page<App> appPage = new Page<>();
        //用户获取应用列表只允许最多20条每页
        int pageSize = queueAppRequest.getPageSize();
        if (pageSize > 20) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "只允许最多20个应用每页");
        }
        appPage.setSize(pageSize);
        appPage.setCurrent(queueAppRequest.getCurrent());
        appPage = appService.page(appPage, queueWrapper);
        Page<AppVO> appVOPage = appService.getPageVO(appPage);
        return Results.success(appVOPage);
    }

    /**
     * 获取精选应用列表
     *
     * @param queueAppRequest
     * @return
     */
    @PostMapping("/good/list/page/vo")
    public Result<Page<AppVO>> goodListPageVO(@RequestBody QueueAppRequest queueAppRequest) {
        queueAppRequest.setPriority(AppConstant.FEATURED_PRIORITY);
        QueryWrapper<App> queueWrapper = appService.getQueueWrapper(queueAppRequest);
        Page<App> appPage = new Page<>();
        appPage.setSize(queueAppRequest.getPageSize());
        appPage.setCurrent(queueAppRequest.getCurrent());
        appPage = appService.page(appPage, queueWrapper);
        Page<AppVO> appVOPage = appService.getPageVO(appPage);
        return Results.success(appVOPage);
    }

    @PostMapping("/admin/list/page")
    @Admin
    public Result<Page<App>> listPageApp(@RequestBody QueueAppRequest queueAppRequest) {
        QueryWrapper<App> queueWrapper = appService.getQueueWrapper(queueAppRequest);
        Page<App> appPage = new Page<>();
        int pageSize = queueAppRequest.getPageSize();
        appPage.setSize(pageSize);
        appPage.setCurrent(queueAppRequest.getCurrent());
        appPage = appService.page(appPage, queueWrapper);
        return Results.success(appPage);
    }

    @PostMapping("/deploy")
    public Result<String> deploy(Long appId, HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        App app = appService.getById(appId);
        if (app == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "应用不存在");
        }
        if (!app.getUserId().equals(loginUser.getId())) {
            throw new BusinessException(ErrorCode.NO_AUTH, "没有权限部署该应用");
        }
        String deployUrl = appService.deploy(app);
        return Results.success(deployUrl);
    }


    /**
     * 应用聊天生成代码（流式 SSE）
     *
     * @param appId   应用 ID
     * @param message 用户消息
     * @param request 请求对象
     * @return 生成结果流
     */
    @GetMapping(value = "/chat/gen/code", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<String>> chatToGenCode(@RequestParam Long appId,
                                                       @RequestParam String message,
                                                       HttpServletRequest request) {
        try {
            // 参数校验
            ThrowUtil.throwIf(appId == null || appId <= 0, new BusinessException(ErrorCode.PARAMS_ERROR, "应用ID无效"));
            ThrowUtil.throwIf(StrUtil.isBlank(message), new BusinessException(ErrorCode.PARAMS_ERROR, "用户消息不能为空"));
            // 获取当前登录用户
            User loginUser = userService.getLoginUser(request);
            // 调用服务生成代码（流式）
            Flux<String> stringFlux = appService.chatToGenCode(appId, message, loginUser);
            return stringFlux.map(check -> {
                Map<String, String> data = Map.of("d", check);
                String jsonStr = JSONUtil.toJsonStr(data);
                return ServerSentEvent.<String>builder()
                        .data(jsonStr)
                        .build();
            }).concatWith(Mono.just(ServerSentEvent.<String>builder()
                    .event("done")
                    .data("")
                    .build()));
        } catch (BusinessException e) {
            // 将业务异常转换为 SSE 错误事件，业务异常处理无法返回Result.error这个方法的返回类型是Flux<ServerSentEvent<String>>
            Map<String, Object> errorData = Map.of(
                    "code", e.getCode(),
                    "message", e.getMessage()
            );
            String errorJson = JSONUtil.toJsonStr(errorData);
            return Flux.just(ServerSentEvent.<String>builder()
                    .event("error")
                    .data(errorJson)
                    .build());
        }
    }

    @PostMapping("/get/history/message")
    public Result<Page<Message>> getHistoryMessage(@RequestBody ChatHistoryQueryRequest chatHistoryQueryRequest,
                                                   HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        Page<Message> historyMessage = messageService.listPageHistoryMessage(chatHistoryQueryRequest, loginUser);
        return Results.success(historyMessage);
    }

}




