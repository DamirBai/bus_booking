package com.bus.booking.repository;

import com.bus.booking.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository  extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByRefreshToken(String refreshToken);
    void deleteByRefreshToken(String refreshToken);
    void deleteById(Long id);
}
