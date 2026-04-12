package com.econauts.Entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String token;
    private LocalDateTime expiryDate;

    public PasswordResetToken() {}

    public PasswordResetToken(String email, String token, LocalDateTime expiryDate) {
        this.email = email;
        this.token = token;
        this.expiryDate = expiryDate;
    }

    public String getEmail() { return email; }
    public String getToken() { return token; }
    public LocalDateTime getExpiryDate() { return expiryDate; }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setExpiryDate(LocalDateTime expiryDate) {
		this.expiryDate = expiryDate;
	}
    
    
}