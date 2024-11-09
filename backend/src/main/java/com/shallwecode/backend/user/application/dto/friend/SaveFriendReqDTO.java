package com.shallwecode.backend.user.application.dto.friend;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaveFriendReqDTO {

    @NotNull(message = "Null이면 안됩니다.")
    private Long toUserId;
}
