package com.shallwecode.backend.problem.application.service;

import com.shallwecode.backend.common.exception.CustomException;
import com.shallwecode.backend.common.exception.ErrorCode;
import com.shallwecode.backend.common.util.CustomUserUtils;
import com.shallwecode.backend.problem.application.dto.*;
import com.shallwecode.backend.problem.domain.service.ProblemDomainService;
import com.shallwecode.backend.user.application.dto.user.FindUserDTO;
import com.shallwecode.backend.user.domain.aggregate.AuthType;
import com.shallwecode.backend.user.domain.service.UserDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProblemService {

    private final ProblemDomainService problemDomainService;
    private final UserDomainService userDomainService;

    public List<FindProblemResDTO> findAllMyProblem(Long userId) {

        return problemDomainService.findAllMyProblem(userId);
    }

    public List<ProblemOneResDTO> selectOneProblem(Long problemId) {
        return problemDomainService.selectOneProblem(problemId);
    }

    public Long findAllNoTryProblemCount(Long userId) {
        return problemDomainService.findAllNoTryProblemCount(userId);
    }

    public Long findAllUnSolvedProblemCount(Long userId) {
            return problemDomainService.findAllUnSolvedProblemCount(userId);
    }

    public Long findAllSolvedProblemCount(long userId) {
        return problemDomainService.findAllSolvedProblemCount(userId);
    }

    public void deleteProblem(Long problemId) {

        Long loginUserId = CustomUserUtils.getCurrentUserSeq();
        FindUserDTO userInfo = userDomainService.findById(loginUserId);

        if(userInfo.getAuth() != AuthType.ADMIN){
            throw new CustomException(ErrorCode.NOT_ADMIN_DELETE_PROBLEM);
        }

        problemDomainService.deleteProblem(problemId);
    }

    public void updateProblem(Long problemId, ProblemReqDTO newProblemInfo) {

        Long loginUserId = CustomUserUtils.getCurrentUserSeq();
        FindUserDTO userInfo = userDomainService.findById(loginUserId);

        if(userInfo.getAuth() != AuthType.ADMIN){
            throw new CustomException(ErrorCode.NOT_ADMIN_UPDATE_PROBLEM);
        }

        problemDomainService.updateProblem(problemId, newProblemInfo);
    }

    public void saveProblem(ProblemReqDTO newProblemInfo) {

        Long loginUserId = CustomUserUtils.getCurrentUserSeq();
        FindUserDTO userInfo = userDomainService.findById(loginUserId);

        if(userInfo.getAuth() != AuthType.ADMIN){
            throw new CustomException(ErrorCode.NOT_ADMIN_SAVE_PROBLEM);
        }

        problemDomainService.saveProblem(newProblemInfo);
    }

    public ProblemListResDTO selectProblemList(Integer page, Long size, String keyword, Integer option) {
        Long offset = (page - 1) * size;

        System.out.println(offset);

        List<ProblemDTO> problemDTO = problemDomainService.selectProblemList(keyword, option, offset, size);
        Long problemCount = problemDomainService.selectProblemCount();

        return ProblemListResDTO.builder()
                .problemList(problemDTO)
                .currentPage(page)
                .totalPages((int) Math.ceil((double) problemCount / size))
                .totalItems(problemCount)
                .build();
    }

    public List<FindProblemResDTO> findAllProblem(ProblemSearchFilter filter) {

        return problemDomainService.findAllProblem(filter);
    }
}
