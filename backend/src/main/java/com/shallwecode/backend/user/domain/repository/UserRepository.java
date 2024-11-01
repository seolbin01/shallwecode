package com.shallwecode.backend.user.domain.repository;

import com.shallwecode.backend.user.domain.aggregate.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
