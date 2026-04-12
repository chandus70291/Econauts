package com.econauts.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.econauts.Entity.PasswordResetToken;

import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    Optional<PasswordResetToken> findByToken(String token);
}