package com.app.api.repository;

import com.app.api.entity.UserRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorizationRepository extends JpaRepository<UserRefreshToken, Long> {
    Optional<UserRefreshToken> findByUser_Id(Long userId);
}
