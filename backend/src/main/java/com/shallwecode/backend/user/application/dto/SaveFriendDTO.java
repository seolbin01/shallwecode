package com.shallwecode.backend.user.application.dto;

import com.shallwecode.backend.user.domain.aggregate.FriendStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaveFriendDTO {
    private Long friendId;
    private FindUserDTO fromUser;
    private FindUserDTO toUser;
    private FriendStatus friendStatus;
}
