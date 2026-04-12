package com.econauts.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.econauts.Entity.LoginRequest;
import com.econauts.Entity.RegisterRequest;
import com.econauts.Entity.User;
import com.econauts.Repo.UserRepository;
import com.econauts.enums.Role;
import com.econauts.security.JwtUtil;

@Service
public class AuthService {

	private static final Logger log = LoggerFactory.getLogger(AuthService.class);
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String register(RegisterRequest request) {

        // ✅ Check duplicate email
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setEmail(request.getEmail());
        user.setPhonenumber(request.getPhonenumber());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);

        userRepository.save(user);

        return jwtUtil.generateToken(user.getEmail());
    }
    
//    public AuthResponse login(LoginRequest request) {
//
//        User user = userRepository.findByEmail(request.getEmail())
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
//            throw new RuntimeException("Invalid credentials");
//        }
//
//        String token = jwtUtil.generateToken(user.getEmail());
//
//        return new AuthResponse(token, user.getEmail(), user.getRole());
//    }

    public String login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        log.info("User logged in: {}", user.getEmail());

        return jwtUtil.generateToken(user.getEmail());
    }
}