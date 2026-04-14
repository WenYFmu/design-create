package com.wyf.designcreate.ai.tokenstream;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class RetrievedMessage extends BaseStreamMessage {

    @Serial
    private static final long serialVersionUID = 1L;

    private List<Object> contents;

    public RetrievedMessage(List<Object> contents) {
        super(StreamMessageType.RETRIEVED);
        this.contents = contents;
    }
}
