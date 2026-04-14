package com.wyf.designcreate.ai.tokenstream;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@Data
@EqualsAndHashCode(callSuper = true)
public class CompleteResponseMessage extends BaseStreamMessage {

    @Serial
    private static final long serialVersionUID = 1L;

    private Object response;

    public CompleteResponseMessage(Object response) {
        super(StreamMessageType.COMPLETE_RESPONSE);
        this.response = response;
    }
}
