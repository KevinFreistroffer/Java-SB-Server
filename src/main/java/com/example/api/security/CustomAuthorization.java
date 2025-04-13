package com.example.api.security;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import com.example.api.model.Role;

@Component
public class CustomAuthorization {
    
    public boolean isAuthorized(
      // String requiredRole
      ) {
      return false;
        // Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        // if (authentication == null || !authentication.isAuthenticated()) {
        //     return false;
        // }
        
        // return authentication.getAuthorities().stream()
        //     .anyMatch(authority -> authority.getAuthority().equals(requiredRole));
    }

    public boolean hasRole(Role role) {
      return true;
    }
} 