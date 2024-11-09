package com.shallwecode.backend.user.application.dto.friend;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FindFriendDetailDTO {

    private Long userId;
    private String nickname;
    private Long finishedProblemCnt;
    private Long notFinishedProblemCnt;
    private Long doingProblemCnt;
}
