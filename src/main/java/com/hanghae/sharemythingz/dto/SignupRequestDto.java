package com.hanghae.sharemythingz.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {
    private String username;
    private String password;
    private String email;
    private String image_url;
    private boolean admin = false;
    private String adminToken = "";

    public boolean isAdmin() {
        return admin;
    }

}
