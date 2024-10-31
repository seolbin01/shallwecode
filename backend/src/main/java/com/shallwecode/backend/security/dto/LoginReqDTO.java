package com.shallwecode.backend.security.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginReqDTO {
    private String userId;
    private String pwd;
}
