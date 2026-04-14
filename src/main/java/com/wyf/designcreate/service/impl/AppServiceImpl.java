package com.wyf.designcreate.service.impl;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wyf.designcreate.ai.aiserver.AiServiceFacade;
import com.wyf.designcreate.ai.aiserver.title.AiPromptProcessService;
import com.wyf.designcreate.ai.message.service.MessageService;
import com.wyf.designcreate.ai.model.enums.CodeTypeEnum;
import com.wyf.designcreate.common.ErrorCode;
import com.wyf.designcreate.constant.AppConstant;
import com.wyf.designcreate.constant.MessageConstant;
import com.wyf.designcreate.exception.BusinessException;
import com.wyf.designcreate.model.dto.app.AddAppRequest;
import com.wyf.designcreate.model.dto.app.QueueAppRequest;
import com.wyf.designcreate.model.dto.app.UpdateAppRequest;
import com.wyf.designcreate.model.entity.App;
import com.wyf.designcreate.model.entity.Message;
import com.wyf.designcreate.model.entity.User;
import com.wyf.designcreate.model.vo.AppVO;
import com.wyf.designcreate.model.vo.UserVO;
import com.wyf.designcreate.service.AppService;
import com.wyf.designcreate.mapper.AppMapper;
import com.wyf.designcreate.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * @author 15502
 * @description 针对表【app(应用)】的数据库操作Service实现
 * @createDate 2026-03-31 21:52:29
 */
@Service
public class AppServiceImpl extends ServiceImpl<AppMapper, App>
        implements AppService {

    @Resource
    private AiServiceFacade aiServiceFacade;

    @Resource
    private UserService userService;

    @Resource
    private AiPromptProcessService aiPromptProcessService;

    @Resource
    private MessageService messageService;

    @Override
    public Long addApp(AddAppRequest addAppRequest, User user) {
        String appName = addAppRequest.getAppName();
        String initPrompt = addAppRequest.getInitPrompt();
        String codeGenType = addAppRequest.getCodeGenType();
        if (StrUtil.isBlank(initPrompt)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "初始提示词不能为空");
        }
        if (CodeTypeEnum.getCodeTypeEnumByValue(codeGenType) == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "代码生成类型错误");
        }
        if (StrUtil.isBlank(appName)) {
            appName = aiPromptProcessService.extractAppName(initPrompt);
        }
        App app = new App();
        app.setAppName(appName);
        app.setInitPrompt(initPrompt);
        app.setUserId(user.getId());
        app.setCodeGenType(codeGenType);
        boolean res = this.save(app);
        if (res) {
            return app.getId();
        } else {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "添加应用失败");
        }
    }

    @Override
    public boolean updateApp(UpdateAppRequest updateAppRequest, User loginUser) {
        Long appId = updateAppRequest.getId();
        App app = this.getById(appId);
        if (app == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "应用不存在");
        }
        if (!app.getUserId().equals(loginUser.getId()) && !userService.isAdmin(loginUser)) {
            throw new BusinessException(ErrorCode.NO_AUTH, "没有权限修改该应用");
        }
        BeanUtil.copyProperties(updateAppRequest, app);
        return this.updateById(app);
    }

    @Override
    public boolean deleteApp(Long appId, User loginUser) {
        App app = this.getById(appId);
        if (app == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "应用不存在");
        }
        if (!app.getUserId().equals(loginUser.getId()) && !userService.isAdmin(loginUser)) {
            throw new BusinessException(ErrorCode.NO_AUTH, "没有权限删除该应用");
        }
        return this.removeById(appId);
    }

    @Override
    public QueryWrapper<App> getQueueWrapper(QueueAppRequest queueAppRequest) {
        if (queueAppRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数错误");
        }
        Long id = queueAppRequest.getId();
        String appName = queueAppRequest.getAppName();
        String codeGenType = queueAppRequest.getCodeGenType();
        String deployKey = queueAppRequest.getDeployKey();
        Date deployedTime = queueAppRequest.getDeployedTime();
        int priority = queueAppRequest.getPriority();
        Long userId = queueAppRequest.getUserId();
        String sortField = queueAppRequest.getSortField();
        String sortOrder = queueAppRequest.getSortOrder();

        QueryWrapper<App> queryWrapper = new QueryWrapper<>();
        return queryWrapper.eq(ObjUtil.isNotNull(queueAppRequest.getId()), "id", id)
                .eq(ObjUtil.isNotNull(queueAppRequest.getUserId()), "userId", userId)
                .eq(StrUtil.isNotBlank(queueAppRequest.getCodeGenType()), "codeGenType", codeGenType)
                .eq(StrUtil.isNotBlank(queueAppRequest.getDeployKey()), "deployKey", deployKey)
                .eq(ObjUtil.isNotNull(queueAppRequest.getDeployedTime()), "deployedTime", deployedTime)
                .eq(ObjUtil.isNotNull(queueAppRequest.getPriority()), "priority", priority)
                .like(StrUtil.isNotBlank(queueAppRequest.getAppName()), "appName", appName)
                .orderBy(StrUtil.isNotBlank(sortField), sortOrder.equals("ascend"), sortField);
    }

    @Override
    public String deploy(App app) {
        String deployKey = app.getDeployKey();

        //获取源文件夹目录
        String sourcePath = getAppFile(app);
        File sourceFile = new File(sourcePath);
        if (!sourceFile.exists() || !sourceFile.isDirectory()) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "应用代码不存在，请先生成应用代码");
        }
        //生成部署key 8位数字字母
        if (StrUtil.isBlank(deployKey)) {
            do {
                deployKey = RandomUtil.randomString(8);
            } while (this.query().eq("deployKey", deployKey).exists());
        }
        //生成部署文件
        String deployDirPath = AppConstant.CODE_DEPLOY_ROOT_DIR + File.separator + deployKey;
        if (!FileUtil.exist(deployDirPath)) {
            FileUtil.mkdir(deployDirPath);
        }
        File deployFile = new File(deployDirPath);
        //拷贝源文件到部署目录覆盖
        try {
            FileUtil.copyContent(sourceFile, deployFile, true);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "应用部署失败请重试");
        }
        //更新应用信息
        app.setDeployKey(deployKey);
        app.setDeployedTime(new Date());
        boolean res = this.updateById(app);
        if (!res) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "应用部署失败请重试");
        }
        //返回应用访问链接
        return String.format("%s/%s", AppConstant.CODE_DEPLOY_HOST, deployKey);
    }

    @Override
    public Flux<String> chatToGenCode(Long appId, String message, User loginUser) {
        //鉴权
        App app = this.getById(appId);
        if (!app.getUserId().equals(loginUser.getId())) {
            throw new BusinessException(ErrorCode.NO_AUTH, "没有权限编辑该应用");
        }
        CodeTypeEnum codeTypeEnum = CodeTypeEnum.getCodeTypeEnumByValue(app.getCodeGenType());
        if (codeTypeEnum == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "代码生成类型错误");
        }

        StringBuilder stringBuilder = new StringBuilder();
        return aiServiceFacade.generateCodeAsSaveStream(message, codeTypeEnum, appId)
                .doOnNext(stringBuilder::append)
                .doOnComplete(() -> {
                    //插入数据库
                    Message userMessage = new Message();
                    userMessage.setAppId(appId);
                    userMessage.setUserId(loginUser.getId());
                    userMessage.setType(MessageConstant.USER);
                    userMessage.setContent(message);
                    messageService.save(userMessage);
                    Message aiMessage = new Message();
                    aiMessage.setAppId(appId);
                    aiMessage.setUserId(loginUser.getId());
                    aiMessage.setType(MessageConstant.AI);
                    aiMessage.setContent(stringBuilder.toString());
                    messageService.save(aiMessage);
                });
    }

    @Override
    public AppVO getAppVO(App app) {
        AppVO appVO = new AppVO();
        BeanUtil.copyProperties(app, appVO);
        if (app.getUserId() != null) {
            User user = userService.getById(app.getUserId());
            userService.getUserVO(user);
            appVO.setUserVO(userService.getUserVO(user));
        }
        return appVO;
    }

    @Override
    public Page<AppVO> getPageVO(Page<App> appPage) {
        //获取所需的用户id
        List<App> AppList = appPage.getRecords();
        Set<Long> userIdSet = AppList.stream().map(App::getUserId).collect(Collectors.toSet());
        Map<Long, UserVO> userMap = userService.listByIds(userIdSet).stream()
                .map(user -> userService.getUserVO(user))
                .collect(Collectors.toMap(UserVO::getId, userVO -> userVO));
        //映射填充
        List<AppVO> appVOList = AppList.stream()
                .map(app -> {
                    AppVO appVO = new AppVO();
                    BeanUtil.copyProperties(app, appVO);
                    appVO.setUserVO(userMap.get(app.getUserId()));
                    return appVO;
                }).toList();
        return new Page<AppVO>(appPage.getCurrent(), appPage.getSize(), appPage.getTotal())
                .setRecords(appVOList);
    }

    /**
     * 获取应用文件夹
     *
     * @param app
     * @return
     */
    private String getAppFile(App app) {
        String codeType = app.getCodeGenType();
        String uniqueName = String.format("%s_%s", codeType, app.getId());
        return AppConstant.CODE_OUTPUT_ROOT_DIR + File.separator + uniqueName;
    }
}















