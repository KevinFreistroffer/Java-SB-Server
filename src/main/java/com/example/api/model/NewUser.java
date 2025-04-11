package com.example.api.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public class NewUser {
  @NotNull(message="Username is required")
  private String username;

  @NotNull(message="Email is required")
  @Email(message="Please provide a valid email address")
  private String email;

  @NotNull(message="Password is required")
  private String password;
  
  public NewUser(String username, String email, String password) {
    this.username = username;
    this.email = email;
    this.password = password;
  }

  public String getUsername() {
    return this.username;
  }
  public String getEmail() {
    return this.email;
  }
  public String getPassword() {
    return this.password;
  }
}
