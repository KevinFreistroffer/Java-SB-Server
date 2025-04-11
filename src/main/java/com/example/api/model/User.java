package com.example.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="users")
public class User {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private long id;
  
  @Column(unique=true)
  private String username;

  @Column(unique=true)
  @Email(message="Email should be valid.")
  private String email;

  @Column(unique=false, name="hashed_password")
  private String hashedPassword;
  
  public User() {
  }
  
  public User(String username, String email, String hashedPassword) {
    this.username = username;
    this.email = email;
    this.hashedPassword = hashedPassword;
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return this.username;
  }

  public String getEmail() {
    return this.email;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setHashedPasword(String hashedPassword) {
    this.hashedPassword = hashedPassword;
  }
}
