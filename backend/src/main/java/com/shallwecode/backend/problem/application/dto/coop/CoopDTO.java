package com.shallwecode.backend.problem.application.dto.coop;

import com.querydsl.core.annotations.QueryProjection;
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

    @QueryProjection
    public CoopDTO(Long codingRoomId, Long userId, boolean isHost) {
        this.codingRoomId = codingRoomId;
        this.userId = userId;
        this.isHost = isHost;
    }

}
