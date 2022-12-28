package com.app.api.entity;

public enum UserRoleEnum {
    ADMIN(Authority.ADMIN),
    USER(Authority.USER);

    private final String authority;

    UserRoleEnum(String authority){
        this.authority = authority;
    }

    public String getAuthority(){
        return this.authority;
    }

    public static class Authority{
        public final static String ADMIN = "ROLE_ADMIN";
        public final static String USER = "ROLE_USER";
    }
}
