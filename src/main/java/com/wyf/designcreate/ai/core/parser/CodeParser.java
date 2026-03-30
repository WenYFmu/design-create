package com.wyf.designcreate.ai.core.parser;

public interface CodeParser<T> {
    /**
     * 解析代码
     */
    T parse(String codeContent);
}
