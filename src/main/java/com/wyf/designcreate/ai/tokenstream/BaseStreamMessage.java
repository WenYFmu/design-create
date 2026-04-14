package com.wyf.designcreate.ai.tokenstream;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public abstract class BaseStreamMessage implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private StreamMessageType type;

    protected BaseStreamMessage(StreamMessageType type) {
        this.type = type;
    }

    public BaseStreamMessage() {
    }
}
