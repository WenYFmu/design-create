package com.wyf.designcreate.ai.tokenstream;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@Data
@EqualsAndHashCode(callSuper = true)
public class ErrorMessage extends BaseStreamMessage {

    @Serial
    private static final long serialVersionUID = 1L;

    private String errorMessage;

    private String errorType;

    public ErrorMessage(String errorMessage, String errorType) {
        super(StreamMessageType.ERROR);
        this.errorMessage = errorMessage;
        this.errorType = errorType;
    }

    public ErrorMessage(String errorMessage) {
        this(errorMessage, null);
    }
}
