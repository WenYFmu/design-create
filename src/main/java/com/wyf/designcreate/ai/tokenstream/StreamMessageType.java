package com.wyf.designcreate.ai.tokenstream;

import lombok.Getter;

@Getter
public enum StreamMessageType {

    PARTIAL_RESPONSE("partial_response", "部分响应"),

    PARTIAL_THINKING("partial_thinking", "部分思考"),

    RETRIEVED("retrieved", "检索内容"),

    INTERMEDIATE_RESPONSE("intermediate_response", "中间响应"),

    BEFORE_TOOL_EXECUTION("before_tool_execution", "工具执行"),

    TOOL_EXECUTED("tool_executed", "工具执行完成"),

    COMPLETE_RESPONSE("complete_response", "完整响应"),

    ERROR("error", "错误");

    private final String code;

    private final String description;

    StreamMessageType(String code, String description) {
        this.code = code;
        this.description = description;
    }

}
