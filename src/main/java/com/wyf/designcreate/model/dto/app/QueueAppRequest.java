package com.wyf.designcreate.model.dto.app;

import com.wyf.designcreate.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class QueueAppRequest extends PageRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -840574557166457514L;

    private Long id;

    private String appName;

    private String codeGenType;

    private String deployKey;

    private Date deployedTime;

    private int priority;

    private Long userId;
}

