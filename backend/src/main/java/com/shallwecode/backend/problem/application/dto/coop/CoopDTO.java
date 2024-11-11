package com.shallwecode.backend.problem.application.dto.coop;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class CoopDTO {

    private Long codingRoomId;
    private Long userId;
    private boolean isHost;



}
