package com.shallwecode.backend.problem.application.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class SendChatDTO {
    private Long userId;
    private String chatContent;
    private String userNickname;
    private LocalDateTime sendTime;
}
