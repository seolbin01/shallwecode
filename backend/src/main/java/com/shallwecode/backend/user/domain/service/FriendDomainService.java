package com.shallwecode.backend.user.domain.service;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shallwecode.backend.common.exception.CustomException;
import com.shallwecode.backend.common.exception.ErrorCode;
import com.shallwecode.backend.user.application.dto.*;
import com.shallwecode.backend.user.domain.aggregate.Friend;
import com.shallwecode.backend.user.domain.aggregate.FriendStatus;
import com.shallwecode.backend.user.domain.aggregate.QFriend;
import com.shallwecode.backend.user.domain.aggregate.QUserInfo;
import com.shallwecode.backend.user.domain.repository.FriendRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendDomainService {

    private final FriendRepository friendRepository;
    private final UserDomainService userDomainService;
    private final ModelMapper modelMapper;
    private final JPAQueryFactory queryFactory;

    public SaveFriendDTO save(SaveFriendReqDTO saveFriendReqDTO) {

        Long loginUserId = 3L;
        FindUserDTO fromUserDTO = userDomainService.findById(loginUserId);

        FindUserDTO toUserDTO = userDomainService.findById(saveFriendReqDTO.getToUserId());

        SaveFriendDTO newFriendDTO = new SaveFriendDTO();
        newFriendDTO.setToUser(toUserDTO);
        newFriendDTO.setFromUser(fromUserDTO);

        Friend savedFriend = friendRepository.save(modelMapper.map(newFriendDTO, Friend.class));

        newFriendDTO.setFriendStatus(savedFriend.getFriendStatus());

        return newFriendDTO;
    }

    public boolean isExistFriend(Long loginUserId, Long toUserId) {

        QFriend friend = QFriend.friend;

        Long count = queryFactory.select(friend.count())
                .from(friend)
                .where((friend.fromUser.userId.eq(loginUserId).and(friend.toUser.userId.eq(toUserId)))
                        .or(friend.fromUser.userId.eq(toUserId).and(friend.toUser.userId.eq(loginUserId))))
                .where(friend.friendStatus.eq(FriendStatus.PENDING))
                .fetchOne();
        System.out.println(count);

        return count == null || count > 0;
    }

    public FindFriendDTO findMyFriend(UpdateFriendReqDTO updateFriendReqDTO) {

        Long loginUserId = 3L;
        Friend findfriend = friendRepository.findByFromUser_UserIdAndToUser_UserId(updateFriendReqDTO.getFromUserId(), loginUserId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_FRIEND));

        return modelMapper.map(findfriend, FindFriendDTO.class);
    }

    public void deleteFriend(FindFriendDTO findFriendDTO) {
        friendRepository.deleteById(findFriendDTO.getFriendId());
    }

    public void updateFriendStatus(FindFriendDTO findFriendDTO) {

        Friend findFriend = friendRepository.findById(findFriendDTO.getFriendId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_FRIEND));

        System.out.println(findFriend.getFriendId());

        modelMapper.map(findFriendDTO, findFriend);
        System.out.println(findFriend.getFriendStatus());
    }

    public List<FriendResListDTO> findAllFriend(Long loginUserId) {

        QFriend friend = QFriend.friend;
        QUserInfo user = QUserInfo.userInfo;

        List<FriendResListDTO> list1 = queryFactory
                .select(Projections.constructor(FriendResListDTO.class,
                        friend.fromUser.userId,
                        friend.fromUser.nickname))
                .from(friend)
                .join(friend.toUser, user)
                .where(user.userId.eq(loginUserId)
                        .and(friend.friendStatus.eq(FriendStatus.ACCEPTED)))
                .fetch();

        List<FriendResListDTO> list2 = queryFactory
                .select(Projections.constructor(FriendResListDTO.class,
                        friend.toUser.userId,
                        friend.toUser.nickname))
                .from(friend)
                .join(friend.fromUser, user)
                .where(user.userId.eq(loginUserId)
                        .and(friend.friendStatus.eq(FriendStatus.ACCEPTED)))
                .fetch();

        list1.addAll(list2);
        return list1;
    }

    public List<FriendResListDTO> findAllFriendReq(Long loginUserId) {

        QFriend friend = QFriend.friend;
        QUserInfo user = QUserInfo.userInfo;

        return queryFactory
                .select(Projections.constructor(FriendResListDTO.class,
                        friend.fromUser.userId,
                        friend.fromUser.nickname))
                .from(friend)
                .join(friend.toUser, user)
                .where(user.userId.eq(loginUserId)
                        .and(friend.friendStatus.eq(FriendStatus.PENDING)))
                .fetch();
    }
}
