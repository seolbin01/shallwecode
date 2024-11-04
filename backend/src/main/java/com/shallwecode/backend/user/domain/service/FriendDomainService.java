package com.shallwecode.backend.user.domain.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shallwecode.backend.user.application.dto.FindUserDTO;
import com.shallwecode.backend.user.application.dto.SaveFriendDTO;
import com.shallwecode.backend.user.application.dto.SaveFriendReqDTO;
import com.shallwecode.backend.user.domain.aggregate.Friend;
import com.shallwecode.backend.user.domain.aggregate.FriendStatus;
import com.shallwecode.backend.user.domain.aggregate.QFriend;
import com.shallwecode.backend.user.domain.repository.FriendRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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

        return count != null || count > 0;
    }
}
