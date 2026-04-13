package com.wyf.designcreate.ai.aiserver.summary;


import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

import java.util.List;

public interface AiSummaryProcessService {

    /**
     *
     * @param content 需要生成摘要的文本
     * @return 生成的摘要结果
     */
    @SystemMessage(fromResource = "prompt/app-message-summary-prompt.txt")
    String extractSummary(@UserMessage List<ChatMessage> content, @V("summary_max_chars") String summaryMaxChars);

}
