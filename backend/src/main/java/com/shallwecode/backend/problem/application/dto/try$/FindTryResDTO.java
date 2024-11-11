package com.shallwecode.backend.problem.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class FindTryResDTO {

    private Long tryId;
    private Long userId;
    private Long problemId;
    private String coopList;
    private boolean isSolved;
    private String tryLanguage;
    private String codeContent;
    private LocalDateTime createdAt;
}
