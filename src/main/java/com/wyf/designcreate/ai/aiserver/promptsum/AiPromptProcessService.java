package com.wyf.designcreate.ai.aiserver.promptsum;

import dev.langchain4j.service.SystemMessage;

public interface AiPromptProcessService {

    /**
     * 从初始提示词中提取应用名称
     *
     * @param initPrompt 初始提示词
     * @return 应用名称
     */
    @SystemMessage(fromResource = "prompt/app-name-extract-system-prompt.txt")
    String extractAppName(String initPrompt);
}
