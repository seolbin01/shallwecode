package com.shallwecode.backend.user.domain.service;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shallwecode.backend.common.exception.CustomException;
import com.shallwecode.backend.common.exception.ErrorCode;
import com.shallwecode.backend.problem.domain.aggregate.QProblem;
import com.shallwecode.backend.problem.domain.aggregate.QTry;
import com.shallwecode.backend.user.application.dto.user.FindUserDTO;
import com.shallwecode.backend.user.application.dto.user.FindUserDetailDTO;
import com.shallwecode.backend.user.application.dto.user.UserSaveDTO;
import com.shallwecode.backend.user.application.dto.user.UserUpdateDTO;
import com.shallwecode.backend.user.domain.aggregate.QUserInfo;
import com.shallwecode.backend.user.domain.aggregate.UserInfo;
import com.shallwecode.backend.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserDomainService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final JPAQueryFactory jpaQueryFactory;

    // 회원 가입 시 유효성 검사
    public void validateNewUser(UserSaveDTO saveUserDTO) {
        if (userRepository.existsByEmail(saveUserDTO.getEmail())) {
            throw new CustomException(ErrorCode.NOT_FOUND_USER);
        }
    }

    // 회원 닉네임 수정
    public void updateUser(UserUpdateDTO userUpdateDTO, Long loginUserId) {
        UserInfo userInfo = userRepository.findById(loginUserId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        modelMapper.map(userUpdateDTO, userInfo);
    }

    // 회원 삭제
    public void deleteUser(Long userId){
        userRepository.deleteById(userId);
    }

    // 회원 찾기
    public FindUserDTO findById(Long userId) {
        UserInfo foundUser = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        return modelMapper.map(foundUser, FindUserDTO.class);
    }

    // 회원 가입 시 유효성 검사
    public void save(UserSaveDTO saveUserDTO) {
        UserInfo saveUser = userRepository.findById(saveUserDTO.getUserId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        modelMapper.map(saveUserDTO, saveUser);
        saveUser.updateAuth();
    }

    public Long findFinishedProblemCnt(Long loginUserId) {
        QProblem qProblem = QProblem.problem;
        QTry qTry = QTry.try$;

        return jpaQueryFactory
                .select(qProblem.problemId.countDistinct())
                .from(qProblem)
                .leftJoin(qTry)
                .on(qProblem.problemId.eq(qTry.problemId))
                .where(qTry.userId.eq(loginUserId)
                        .and(qTry.isSolved.eq(true)))
                .fetchOne();
    }

    public Long findDoingProblemCnt(Long loginUserId) {
        QProblem qProblem = QProblem.problem;
        QTry qTry = QTry.try$;

        return jpaQueryFactory
                .select(qProblem.problemId.countDistinct())
                .from(qProblem)
                .leftJoin(qTry)
                .on(qProblem.problemId.eq(qTry.problemId))
                .where(qTry.userId.eq(loginUserId))
                .fetchOne();
    }

    public Long findAllProblemCnt() {
        QProblem qProblem = QProblem.problem;
        QTry qTry = QTry.try$;

        return jpaQueryFactory
                .select(qProblem.count())
                .from(qProblem)
                .fetchOne();
    }


    public FindUserDetailDTO findSimpleInfoById(Long loginUserId) {

        UserInfo userInfo = userRepository.findById(loginUserId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        return modelMapper.map(userInfo, FindUserDetailDTO.class);
    }

    // refreshToken 재저장
    @Transactional
    public void updateRefreshToken(Long userId, String refreshToken) {
        UserInfo userInfo = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        userInfo.updateRefreshToken(refreshToken);
    }

    public List<FindUserDTO> findAllUsers(String nickname) {
        QUserInfo userInfo = QUserInfo.userInfo;

        return jpaQueryFactory
                .select(Projections.constructor(FindUserDTO.class,
                        userInfo.userId,
                        userInfo.email,
                        userInfo.nickname,
                        userInfo.auth))
                .from(userInfo)
                .where(userInfo.nickname.containsIgnoreCase(nickname.trim()))
                .fetch();
    }
}
