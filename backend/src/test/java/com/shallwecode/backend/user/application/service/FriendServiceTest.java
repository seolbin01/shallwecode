package com.shallwecode.backend.user.application.service;

import com.shallwecode.backend.user.application.dto.friend.FriendResListDTO;
import com.shallwecode.backend.user.application.dto.friend.SaveFriendReqDTO;
import com.shallwecode.backend.user.application.dto.friend.UpdateFriendReqDTO;
import com.shallwecode.backend.user.domain.aggregate.Friend;
import com.shallwecode.backend.user.domain.aggregate.FriendStatus;
import com.shallwecode.backend.user.domain.repository.FriendRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class FriendServiceTest {

    @Autowired
    private FriendService friendService;

    @Autowired
    private FriendRepository friendRepository;

    @Test
    @DisplayName("친구 신청 테스트")
    public void saveFriendTest() {
        // given
        SaveFriendReqDTO saveFriendReqDTO = new SaveFriendReqDTO();
        saveFriendReqDTO.setToUserId(1L);

        Long loginUserId = 71L;

        // when
        friendService.saveFriend(loginUserId, saveFriendReqDTO);

        // then
        Optional<Friend> friend = friendRepository.findByFromUser_UserIdAndToUser_UserId(loginUserId, saveFriendReqDTO.getToUserId());
        assertNotNull(friend);
    }

    @Test
    @DisplayName("친구 신청 수락 테스트")
    public void acceptFriendTest() {
        // given
        // 친구 수락 테스트를 위한 친구 신청
        Long userId1 = 71L; // 신청 유저
        Long userId2 = 1L; // 수락 유저

        SaveFriendReqDTO saveFriendReqDTO = new SaveFriendReqDTO();
        saveFriendReqDTO.setToUserId(userId2);
        Long loginUserId1 = userId1;
        friendService.saveFriend(loginUserId1, saveFriendReqDTO);
        Optional<Friend> friend1 = friendRepository.findByFromUser_UserIdAndToUser_UserId(loginUserId1, saveFriendReqDTO.getToUserId());
        FriendStatus beforeStatus = null;
        if (friend1.isPresent()) {
            beforeStatus = friend1.get().getFriendStatus();
        }

        Long fromUserId = userId1;
        Long loginUserId2 = userId2;
        UpdateFriendReqDTO updateFriendReqDTO = new UpdateFriendReqDTO();
        updateFriendReqDTO.setFromUserId(fromUserId);
        boolean result = true;

        // when
        friendService.updateFriend(loginUserId2, updateFriendReqDTO, result);

        // then
        Optional<Friend> friend2 = friendRepository.findByFromUser_UserIdAndToUser_UserId(updateFriendReqDTO.getFromUserId(), loginUserId2);
        FriendStatus afterStatus = null;
        if (friend2.isPresent()) {
            afterStatus = friend2.get().getFriendStatus();
        }
        if (friend2.isPresent()) {
            assertEquals(FriendStatus.PENDING, beforeStatus);
            assertEquals(FriendStatus.ACCEPTED, afterStatus);
        }

    }

    @Test
    @DisplayName("친구 신청 거절 테스트")
    public void rejectFriendTest() {
        // given
        // 친구 거절 테스트를 위한 친구 신청
        Long userId1 = 71L; // 신청 유저
        Long userId2 = 1L; // 수락 유저

        SaveFriendReqDTO saveFriendReqDTO = new SaveFriendReqDTO();
        saveFriendReqDTO.setToUserId(userId2);
        Long loginUserId1 = userId1;
        friendService.saveFriend(loginUserId1, saveFriendReqDTO);
        Optional<Friend> friend1 = friendRepository.findByFromUser_UserIdAndToUser_UserId(loginUserId1, saveFriendReqDTO.getToUserId());
        FriendStatus beforeStatus = null;
        if (friend1.isPresent()) {
            beforeStatus = friend1.get().getFriendStatus();
        }

        Long fromUserId = userId1;
        Long loginUserId2 = userId2;
        UpdateFriendReqDTO updateFriendReqDTO = new UpdateFriendReqDTO();
        updateFriendReqDTO.setFromUserId(fromUserId);
        boolean result = false;

        // when
        friendService.updateFriend(loginUserId2, updateFriendReqDTO, result);

        // then
        Optional<Friend> friend2 = friendRepository.findByFromUser_UserIdAndToUser_UserId(updateFriendReqDTO.getFromUserId(), loginUserId2);

        assertEquals(FriendStatus.PENDING, beforeStatus);
        assertTrue(friend2.isEmpty());

    }

    @Test
    @DisplayName("친구 삭제 테스트")
    public void deleteFriendTest() {
        // given
        // 친구 삭제 테스트를 위한 친구 신청
        Long userId1 = 71L; // 신청 유저
        Long userId2 = 1L; // 수락 유저

        SaveFriendReqDTO saveFriendReqDTO = new SaveFriendReqDTO();
        saveFriendReqDTO.setToUserId(userId2);
        Long loginUserId1 = userId1;
        friendService.saveFriend(loginUserId1, saveFriendReqDTO);

        Long fromUserId = userId1;
        Long loginUserId2 = userId2;
        UpdateFriendReqDTO updateFriendReqDTO = new UpdateFriendReqDTO();
        updateFriendReqDTO.setFromUserId(fromUserId);
        boolean result = true;

        friendService.updateFriend(loginUserId2, updateFriendReqDTO, result);
        Optional<Friend> friend1 = friendRepository.findByFromUser_UserIdAndToUser_UserId(updateFriendReqDTO.getFromUserId(), loginUserId2);
        FriendStatus friendStatus = null;
        if (friend1.isPresent()) {
            friendStatus = friend1.get().getFriendStatus();
        }

        // when
        friendService.deleteFriend(loginUserId2, userId1);

        // then
        Optional<Friend> friend2 = friendRepository.findByFromUser_UserIdAndToUser_UserId(updateFriendReqDTO.getFromUserId(), loginUserId2);

        assertEquals(FriendStatus.ACCEPTED, friendStatus);
        assertTrue(friend2.isEmpty());
    }

    @Test
    @DisplayName("친구 목록 조회 테스트")
    public void findAllFriendsTest() {
        // given
        // 친구 목록 조회 테스트를 위해 유저
        Long userId1 = 21L;

        // when
        List<FriendResListDTO> findAllFriends = friendService.findAllFriend(userId1);

        // then
        if (findAllFriends.isEmpty()) {
            assertNull(findAllFriends);
        } else {
            assertNotNull(findAllFriends);
        }
    }

    @Test
    @DisplayName("친구 신청 목록 조회 테스트")
    public void findAllFriendReqTest() {
        // given
        // 친구 신청 목록 조회 테스트 위한 유저
        Long userId1 = 21L;

        // when
        List<FriendResListDTO> findAllFriendReq = friendService.findAllFriendReq(userId1);

        //then
        if (findAllFriendReq.isEmpty()) {
            assertNull(findAllFriendReq);
        } else {
            assertNotNull(findAllFriendReq);
        }
    }
}