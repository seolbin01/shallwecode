package com.shallwecode.backend.problem.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class SendCodeDTO {
    private Long codingRoomId;
    private Long problemId;
    private String tryLanguage;
    private String codeContent;
}
