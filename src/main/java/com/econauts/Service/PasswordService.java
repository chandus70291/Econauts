package com.econauts.Service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.econauts.Entity.PasswordResetToken;
import com.econauts.Entity.User;
import com.econauts.Repo.PasswordResetTokenRepository;
import com.econauts.Repo.UserRepository;

@Service
public class PasswordService {

    @Autowired
    private PasswordResetTokenRepository tokenRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 🔹 Step 1: Generate token
    public String createResetToken(String email) {

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = UUID.randomUUID().toString();

        PasswordResetToken resetToken = new PasswordResetToken(
                email,
                token,
                LocalDateTime.now().plusMinutes(15) // expiry 15 min
        );

        tokenRepo.save(resetToken);

        return token;
    }

    // 🔹 Step 2: Reset password
    public void resetPassword(String token, String newPassword) {

        PasswordResetToken resetToken = tokenRepo.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid token"));

        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token expired");
        }

        User user = userRepo.findByEmail(resetToken.getEmail())
                .orElseThrow();

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepo.save(user);

        // optional: delete token after use
        tokenRepo.delete(resetToken);
    }
}