package com.econauts.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.econauts.Service.PasswordService;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class PasswordController {

    @Autowired
    private PasswordService passwordService;

    // 🔹 Forgot Password
    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestBody Map<String, String> req) {

        String email = req.get("email");

        String token = passwordService.createResetToken(email);

        // TODO: Send email
        String resetLink = "http://localhost:3000/reset-password?token=" + token;

        return "Reset link: " + resetLink;
    }

    // 🔹 Reset Password
    @PostMapping("/reset-password")
    public String resetPassword(@RequestBody Map<String, String> req) {

        String token = req.get("token");
        String newPassword = req.get("password");

        passwordService.resetPassword(token, newPassword);

        return "Password updated successfully";
    }
}