package com.shallwecode.backend.user.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FindUserDTO {

    private Long userId;
    private String email;
    private String nickname;
    private String auth;
}
