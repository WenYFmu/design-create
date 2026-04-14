package com.wyf.designcreate.ai.tokenstream;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
public class ToolExecutedMessage extends BaseStreamMessage {

    @Serial
    private static final long serialVersionUID = 1L;

    private String toolName;

    private String toolCallId;

    private Object result;

    public ToolExecutedMessage(String toolName, String toolCallId, Object result) {
        super(StreamMessageType.TOOL_EXECUTED);
        this.toolName = toolName;
        this.toolCallId = toolCallId;
        this.result = result;
    }
}
