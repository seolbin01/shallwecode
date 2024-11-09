package com.shallwecode.backend.user.application.dto.user;

import com.shallwecode.backend.user.domain.aggregate.AuthType;
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
    private AuthType auth;
}
