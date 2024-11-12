package com.shallwecode.backend.problem.application.controller;

import com.shallwecode.backend.problem.application.dto.compile.CompileReqDTO;
import com.shallwecode.backend.problem.application.dto.compile.CompileResDTO;
import com.shallwecode.backend.problem.application.dto.compile.SubmitReqDTO;
import com.shallwecode.backend.problem.application.dto.compile.SubmitResDTO;
import com.shallwecode.backend.problem.application.dto.coop.CoopUserResDTO;
import com.shallwecode.backend.problem.application.dto.problem.ProblemOneResDTO;
import com.shallwecode.backend.problem.application.dto.try$.SaveTryReqDTO;
import com.shallwecode.backend.problem.application.service.*;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/compile")
public class CompileController {

    private final CompileService compileService;
    private final ProblemService problemService;
    private final TryService tryService;
    private final CoopService coopService;
    private final CodingRoomService codingRoomService;

    @PostMapping("/run")
    public ResponseEntity<CompileResDTO> runCode(@RequestBody CompileReqDTO compileReqDTO) {

        CompileResDTO compileResDTO = compileService.runCode(compileReqDTO);

        return new ResponseEntity<>(compileResDTO, HttpStatus.OK);
    }

    @Operation(summary = "코드 제출", description = "코드를 제출한다.")
    @PostMapping("/codingroom/{codingroomId}/submission")
    public ResponseEntity<SubmitResDTO> submitCode(
            @PathVariable Long codingroomId,
            @RequestBody SubmitReqDTO submitReqDTO) {

        ProblemOneResDTO problemOneResDTO = problemService.selectOneProblem(submitReqDTO.getProblemId());

        SubmitResDTO submitResDTO = compileService.checkTestCase(submitReqDTO.getCode(), problemOneResDTO);

        List<CoopUserResDTO> coopUserResDTOS = coopService.selectCoopFriend(codingroomId);
        StringBuilder coopList = new StringBuilder();
        List<SaveTryReqDTO> saveTryReqDTOS = new ArrayList<>();

        codingRoomService.updateCode(codingroomId, submitReqDTO.getCode());

        for (int i = 0; i < coopUserResDTOS.size(); i++) {
            SaveTryReqDTO saveTryReqDTO = new SaveTryReqDTO();
            saveTryReqDTO.setCodeContent(submitReqDTO.getCode());
            saveTryReqDTO.setTryLanguage(submitReqDTO.getLanguage());
            saveTryReqDTO.setSolved(submitResDTO.getResult());

            if (i == coopUserResDTOS.size() - 1) {
                coopList.append(coopUserResDTOS.get(i).getUserNickname());
            } else {
                coopList.append(coopUserResDTOS.get(i).getUserNickname()).append(",");
            }

            saveTryReqDTOS.add(saveTryReqDTO);

        }

        for (int i = 0; i < coopUserResDTOS.size(); i++) {
            saveTryReqDTOS.get(i).setCoopList(coopList.toString());
            tryService.saveTry(coopUserResDTOS.get(i).getUserId(), submitReqDTO.getProblemId(), saveTryReqDTOS.get(i));
        }

        return ResponseEntity.ok(submitResDTO);
    }
}
