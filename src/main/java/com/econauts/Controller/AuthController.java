package com.econauts.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.econauts.Entity.LoginRequest;
import com.econauts.Entity.RegisterRequest;
import com.econauts.Service.AuthService;
import com.econauts.dto.ApiResponse;
import com.econauts.dto.AuthResponse;
import com.econauts.security.JwtUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    
    @Autowired
    JwtUtil jwtUtil;
    
    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ApiResponse<String> register(@Valid @RequestBody RegisterRequest request) {
        String token = authService.register(request);
        return new ApiResponse<>(true, "User registered successfully", token);
    }
    
    @PostMapping("/login")
    public ApiResponse<AuthResponse> login(@RequestBody LoginRequest request) {

        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
            )
        );

        String accessToken = jwtUtil.generateToken(request.getEmail());
        String refreshToken = jwtUtil.generateRefreshToken(request.getEmail());

        return new ApiResponse<>(
            true,
            "Login successful",
            new AuthResponse(accessToken, refreshToken)
        );
    }
    
    @PostMapping("/refresh")
    public ApiResponse<AuthResponse> refresh(@RequestBody String refreshToken) {

        String email = jwtUtil.extractUsername(refreshToken);

        if (!jwtUtil.validateToken(refreshToken, email)) {
            throw new RuntimeException("Invalid refresh token");
        }

        String newAccessToken = jwtUtil.generateToken(email);

        return new ApiResponse<>(
            true,
            "Token refreshed",
            new AuthResponse(newAccessToken, refreshToken)
        );
    }
    
//    @PostMapping("/login")
//    public ApiResponse<String> login(@RequestBody LoginRequest request) {
//        String token = authService.login(request);
//        return new ApiResponse<>(true, "Login successful", token);
//    }
}
