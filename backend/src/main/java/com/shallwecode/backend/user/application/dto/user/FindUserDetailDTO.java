package com.shallwecode.backend.user.application.dto.user;

import com.shallwecode.backend.user.domain.aggregate.AuthType;
import com.shallwecode.backend.user.domain.aggregate.SocialType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FindUserDetailDTO {

    private Long userId;
    private SocialType provider;
    private String email;
    private String nickname;
    private AuthType auth;
    private Long finishedProblemCnt;
    private Long notFinishedProblemCnt;
    private Long doingProblemCnt;
}
