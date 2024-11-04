package com.shallwecode.backend.user.domain.service;

import com.shallwecode.backend.user.application.dto.FoundUserDTO;
import com.shallwecode.backend.user.application.dto.SaveFriendDTO;
import com.shallwecode.backend.user.application.dto.SaveFriendReqDTO;
import com.shallwecode.backend.user.application.dto.SaveFriendResDTO;
import com.shallwecode.backend.user.domain.aggregate.Friend;
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
    
    public SaveFriendResDTO save(SaveFriendReqDTO saveFriendReqDTO) {

        Long loginUserId = 1L;
        FoundUserDTO fromUserDTO = userDomainService.findById(loginUserId);

        FoundUserDTO toUserDTO = userDomainService.findById(saveFriendReqDTO.getToUserId());

        SaveFriendDTO newFriendDTO = new SaveFriendDTO();
        newFriendDTO.setToUser(toUserDTO);
        newFriendDTO.setFromUser(fromUserDTO);

        Friend savedFriend = friendRepository.save(modelMapper.map(newFriendDTO, Friend.class));

        return new SaveFriendResDTO(
                modelMapper.map(fromUserDTO, FoundUserDTO.class),
                modelMapper.map(toUserDTO, FoundUserDTO.class),
                savedFriend.getFriendStatus()
        );
    }
}
