package com.shallwecode.backend.user.application.service;

import com.shallwecode.backend.user.application.dto.friend.FindFriendDetailDTO;
import com.shallwecode.backend.user.application.dto.user.FindUserDTO;
import com.shallwecode.backend.user.application.dto.user.FindUserDetailDTO;
import com.shallwecode.backend.user.application.dto.user.UserSaveDTO;
import com.shallwecode.backend.user.application.dto.user.UserUpdateDTO;
import com.shallwecode.backend.user.domain.aggregate.UserInfo;
import com.shallwecode.backend.user.domain.repository.UserRepository;
import com.shallwecode.backend.user.domain.service.UserDomainService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserDomainService userDomainService;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    // 회원 가입
    @Transactional
    public void saveUser(UserSaveDTO userSaveDTO) {
        userDomainService.validateNewUser(userSaveDTO);
        userDomainService.save(userSaveDTO);

    }

    // 회원 닉네임 수정
    @Transactional
    public void updateUser(UserUpdateDTO userUpdateDTO, Long loginUserId) {
        userDomainService.updateUser(userUpdateDTO, loginUserId);
    }

    // 회원 삭제
    @Transactional
    public void deleteUser(Long userId) {
        userDomainService.deleteUser(userId);
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        UserInfo loginUser = userRepository.findById(Long.parseLong(userId))
                .orElseThrow(() -> new UsernameNotFoundException(userId));

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(loginUser.getAuth().toString()));

        return new User(String.valueOf(loginUser.getUserId()), "", grantedAuthorities);
    }

    // 닉네임으로 회원 목록 조회
    @Transactional(readOnly = true)
    public List<FindUserDetailDTO> findUserDetailsByNickname(String nickname) {
        List<FindUserDetailDTO> userDetailList = new ArrayList<>();

        List<FindUserDTO> userList = userDomainService.findAllUsers(nickname);

        Long allProblemCnt = userDomainService.findAllProblemCnt();

        for (FindUserDTO findUserDTO : userList) {
            FindUserDetailDTO userDetailDTO = modelMapper.map(findUserDTO, FindUserDetailDTO.class);
            userDetailList.add(userDetailDTO);

            Long doingProblemCnt = userDomainService.findDoingProblemCnt(findUserDTO.getUserId());
            Long finishedProblemCnt = userDomainService.findFinishedProblemCnt(findUserDTO.getUserId());
            Long notFinishedProblemCnt = allProblemCnt - doingProblemCnt;

            userDetailDTO.setDoingProblemCnt(doingProblemCnt);
            userDetailDTO.setFinishedProblemCnt(finishedProblemCnt);
            userDetailDTO.setNotFinishedProblemCnt(notFinishedProblemCnt);
        }

        return userDetailList;
    }

    public FindUserDetailDTO findUserDetail(Long loginUserId) {
        Long allProblemCnt = userDomainService.findAllProblemCnt();
        Long doingProblemCnt = userDomainService.findDoingProblemCnt(loginUserId);
        Long finishedProblemCnt = userDomainService.findFinishedProblemCnt(loginUserId);

        Long notFinishedProblemCnt = allProblemCnt - doingProblemCnt;

        FindUserDetailDTO findUserDetailDTO = userDomainService.findSimpleInfoById(loginUserId);

        findUserDetailDTO.setDoingProblemCnt(doingProblemCnt);
        findUserDetailDTO.setFinishedProblemCnt(finishedProblemCnt);
        findUserDetailDTO.setNotFinishedProblemCnt(notFinishedProblemCnt);

        return findUserDetailDTO;
    }


    public FindFriendDetailDTO findFriendDetail(Long userId) {

        FindUserDetailDTO userDetailDTO = findUserDetail(userId);
        return modelMapper.map(userDetailDTO, FindFriendDetailDTO.class);
    }
}
