package com.shallwecode.backend.problem.application.dto.compile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubmitResDTO {

    private Boolean result;
    private double score;
}
