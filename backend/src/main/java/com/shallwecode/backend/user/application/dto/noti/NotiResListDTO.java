package com.shallwecode.backend.user.application.dto.noti;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotiResListDTO {

    private Long notiId;
    private Long codingRoomId;
    private String content;
    private Integer isRead;
    private LocalDateTime createdAt;
}
