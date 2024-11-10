package com.shallwecode.backend.user.application.dto.user;

import com.shallwecode.backend.user.domain.aggregate.SocialType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FindUserInfoDTO {

    private Long userId;
    private String email;
    private SocialType provider;
    private String nickname;
    private LocalDateTime createdAt;

}