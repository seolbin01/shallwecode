package com.shallwecode.backend.user.application.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateDTO {
    @NotBlank(message = "닉네임이 빈칸이면 안됩니다.")
    private String nickname;
}
