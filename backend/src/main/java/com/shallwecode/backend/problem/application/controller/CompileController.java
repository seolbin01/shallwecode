package com.shallwecode.backend.problem.application.controller;

import com.shallwecode.backend.problem.application.dto.compile.CompileReqDTO;
import com.shallwecode.backend.problem.application.dto.compile.CompileResDTO;
import com.shallwecode.backend.problem.application.service.CompileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/compile")
public class CompileController {

    private final CompileService compileService;

    @PostMapping("/run")
    public ResponseEntity<CompileResDTO> runCode(@RequestBody CompileReqDTO compileReqDTO) {

        CompileResDTO compileResDTO = compileService.runCode(compileReqDTO);

        return new ResponseEntity<>(compileResDTO, HttpStatus.OK);
    }
}
