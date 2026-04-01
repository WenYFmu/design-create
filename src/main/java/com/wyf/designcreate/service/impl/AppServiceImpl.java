package com.wyf.designcreate.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wyf.designcreate.ai.core.aiserver.codegen.AiCodeServiceFacade;
import com.wyf.designcreate.ai.core.aiserver.promptsum.AiPromptProcessService;
import com.wyf.designcreate.common.ErrorCode;
import com.wyf.designcreate.exception.BusinessException;
import com.wyf.designcreate.model.dto.app.AddAppRequest;
import com.wyf.designcreate.model.entity.App;
import com.wyf.designcreate.model.entity.User;
import com.wyf.designcreate.model.vo.UserVO;
import com.wyf.designcreate.service.AppService;
import com.wyf.designcreate.mapper.AppMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author 15502
 * @description 针对表【app(应用)】的数据库操作Service实现
 * @createDate 2026-03-31 21:52:29
 */
@Service
public class AppServiceImpl extends ServiceImpl<AppMapper, App>
        implements AppService {

    @Resource
    private AiCodeServiceFacade aiCodeServiceFacade;

    @Resource
    private AiPromptProcessService aiPromptProcessService;

    @Override
    public Long addApp(AddAppRequest addAppRequest, User user) {
        String appName = addAppRequest.getAppName();
        String initPrompt = addAppRequest.getInitPrompt();
        if (StrUtil.isBlank(initPrompt)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "初始提示词不能为空");
        }
        if (StrUtil.isBlank(appName)) {
            appName = aiPromptProcessService.extractAppName(initPrompt);
        }
        App app = new App();
        app.setAppName(appName);
        app.setInitPrompt(initPrompt);
        app.setUserId(user.getId());
        boolean res = this.save(app);
        if (res) {
            return app.getId();
        } else {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "添加应用失败");
        }
    }
}















