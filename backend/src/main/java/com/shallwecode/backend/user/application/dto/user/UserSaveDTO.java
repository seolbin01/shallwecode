package com.shallwecode.backend.user.application.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSaveDTO {
    private Long userId;
    private String email;
    private String nickname;
}
