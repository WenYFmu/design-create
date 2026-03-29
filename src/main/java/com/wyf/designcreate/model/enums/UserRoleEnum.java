package com.wyf.designcreate.model.enums;

public enum UserRoleEnum {
    ADMIN("管理员", "admin"),
    USER("用户", "user"),
    ;
    private final String text;
    private final String value;

    UserRoleEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 根据值获取枚举
     * @param role value
     * @return 枚举
     */
    public static UserRoleEnum getUserEnum(String role) {
        if(role==null){
            return null;
        }
        for(UserRoleEnum e : UserRoleEnum.values()) {
            if(e.getValue().equals(role)) {
                return e;
            }
        }
        return null;
    }

    public String getText() {
        return text;
    }

    public String getValue() {
        return value;
    }
}
