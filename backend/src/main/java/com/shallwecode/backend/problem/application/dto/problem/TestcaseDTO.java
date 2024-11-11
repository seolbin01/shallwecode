package com.shallwecode.backend.problem.application.dto.problem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class TestcaseDTO {

    private String input;
    private String output;

}
