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
  
  @Column(unique=false, name="first_name")
  private String firstName;
  
  @Column(unique=false, name="last_name")
  private String lastName;
  
  public User() {
  }
  
  public User(String username, String email, String hashedPassword) {
    this.username = username;
    this.email = email;
    this.hashedPassword = hashedPassword;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getHashedPassword() {
    return hashedPassword;
  }

  public void setHashedPasword(String hashedPassword) {
    this.hashedPassword = hashedPassword;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }
}
