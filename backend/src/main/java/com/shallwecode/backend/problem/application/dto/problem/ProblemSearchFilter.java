package com.shallwecode.backend.problem.application.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProblemSearchFilter {
    private final Long userId;
    private final Boolean isSolved;
    private final Integer problemLevel;

    public boolean isGuestSearch() {
        return userId == null;
    }
}
