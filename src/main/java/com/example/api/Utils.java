package com.example.api;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

// To hash a password
// String hashedPassword = utils.hashPassword(rawPassword);

// To verify a password
// boolean isValid = utils.matches(rawPassword, storedHashedPassword);
 
@Component
public class Utils {
    private final PasswordEncoder passwordEncoder;

    public Utils(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
    
    public String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }

    public boolean matches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
