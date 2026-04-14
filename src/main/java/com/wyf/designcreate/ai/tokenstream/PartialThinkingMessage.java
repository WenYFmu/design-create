package com.wyf.designcreate.ai.tokenstream;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@Data
@EqualsAndHashCode(callSuper = true)
public class PartialThinkingMessage extends BaseStreamMessage {

    @Serial
    private static final long serialVersionUID = 1L;

    private String thinking;

    public PartialThinkingMessage(String thinking) {
        super(StreamMessageType.PARTIAL_THINKING);
        this.thinking = thinking;
    }
}
