package com.wyf.designcreate.exception;

public class ThrowUtil {
    public static void throwIf(boolean condition, RuntimeException e) {
        if (condition) {
            throw e;
        }
    }
}
