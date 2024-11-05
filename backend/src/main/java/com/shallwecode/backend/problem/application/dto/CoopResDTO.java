package com.shallwecode.backend.problem.application.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class CoopResDTO {

    private Long coopId;
    private Long codingRoomId;
    private Long userId;
    private boolean isHost;

}
