package com.shallwecode.backend.problem.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class FindMyTryResDTO {

    private Long tryId;
    private String coopList;
    private boolean isSolved;
    private String tryLanguage;
    private LocalDateTime createdAt;
}
