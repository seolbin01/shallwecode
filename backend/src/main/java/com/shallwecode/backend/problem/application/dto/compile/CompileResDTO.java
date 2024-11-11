package com.shallwecode.backend.problem.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompileResDTO {

    private String output;
    private String compileError;
    private String runtimeError;
    private String systemError;
}
