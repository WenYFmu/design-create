package com.wyf.designcreate.ai.model.enums;

import lombok.Getter;

/**
 * 应用类型枚举
 */
@Getter
public enum CodeTypeEnum {
    HTML("原生 HTML", "html"),

    MULTI_FILE("原生多文件模式", "multi_file"),
    ;
    private final String text;

    private final String value;

    CodeTypeEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }

    public static CodeTypeEnum getCodeTypeEnumByValue(String value) {
        for (CodeTypeEnum codeTypeEnum : CodeTypeEnum.values()) {
            if (codeTypeEnum.value.equals(value)) {
                return codeTypeEnum;
            }
        }
        return null;
    }
}
