package com.shallwecode.backend.user.domain.repository;

import com.shallwecode.backend.user.domain.aggregate.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {
}
