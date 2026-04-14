package com.wyf.designcreate.ai.tokenstream;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@Data
@EqualsAndHashCode(callSuper = true)
public class IntermediateResponseMessage extends BaseStreamMessage {

    @Serial
    private static final long serialVersionUID = 1L;

    private Object response;

    public IntermediateResponseMessage(Object response) {
        super(StreamMessageType.INTERMEDIATE_RESPONSE);
        this.response = response;
    }
}
