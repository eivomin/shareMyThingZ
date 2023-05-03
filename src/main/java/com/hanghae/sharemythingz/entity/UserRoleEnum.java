package com.hanghae.sharemythingz.entity;

public enum UserRoleEnum {
    USER(Authority.USER),   //사용자 권한
    ADMIN(Authority.ADMIN),
    SOCIAL(Authority.SOCIAL);


    private final String authority;

    UserRoleEnum(String authority) {
        this.authority = authority;
    }

    public String getAuthority(){
        return this.authority;
    }

    public static class Authority{
        public static final String USER = "ROLE_USER";
        public static final String ADMIN = "ROLE_ADMIN";
        public static final String SOCIAL = "ROLE_SOCIAL";
    }
}
