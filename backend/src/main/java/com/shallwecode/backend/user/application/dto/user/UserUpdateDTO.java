package com.shallwecode.backend.user.application.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateDTO {
    @NotBlank
    private Long userId;
    private String nickName;
}
