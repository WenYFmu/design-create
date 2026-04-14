package com.wyf.designcreate.ai.tokenstream;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
public class BeforeToolExecutionMessage extends BaseStreamMessage {

    @Serial
    private static final long serialVersionUID = 1L;

    private String toolName;

    private String toolCallId;

    private Map<String, Object> arguments;

    public BeforeToolExecutionMessage(String toolName, String toolCallId, Map<String, Object> arguments) {
        super(StreamMessageType.BEFORE_TOOL_EXECUTION);
        this.toolName = toolName;
        this.toolCallId = toolCallId;
        this.arguments = arguments;
    }

}
