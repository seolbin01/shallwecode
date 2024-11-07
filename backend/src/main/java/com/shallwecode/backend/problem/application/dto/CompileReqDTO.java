package com.shallwecode.backend.problem.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompileReqDTO {

    private String code;
    private String language;
}
