package com.shallwecode.backend.user.application.dto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserSaveDTO {
    private Long userId;
    private String provider;
    private String ProviderId;
    private String email;
    private String nickname;
    private String auth;
    private Date createdAt;
}
