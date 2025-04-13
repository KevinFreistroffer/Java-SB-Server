package com.example.api.service;

import com.example.api.model.NewUser;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.api.repository.UserRepository;
import com.example.api.model.User;
import com.example.api.model.UserDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.ArrayList;
import java.util.List;

import com.example.api.Utils;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private Utils utils;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
    System.out.println("UserService!");
  }

  public UserDTO createUser(NewUser newUser) {
    System.out.println(newUser);
    System.out.println(newUser);
    System.out.println(newUser);
    System.out.println(newUser);
    // Check if user with this email already exists
    User existingUser = this.userRepository.findUserByEmail(newUser.getEmail());
    if (existingUser != null) {
      throw new IllegalArgumentException("A user with this email already exists");
    }

    // Check if user with this username already exists
    existingUser = this.userRepository.findUserByUsername(newUser.getUsername());
    if (existingUser != null) {
      throw new IllegalArgumentException("A user with this username already exists");
    }
    String hashed_password = utils.hashPassword(newUser.getPassword());
    System.out.println("Hashed password = " + hashed_password);
    User user = new User(newUser.getUsername(), newUser.getEmail(), hashed_password);
    this.userRepository.save(user);
    UserDTO userDTO = new UserDTO(user.getId(), user.getUsername(), user.getEmail());
    return userDTO;
  }

  public UserDTO getUserById(@NotNull(message="id argument is required") Long id) {
    User user = this.userRepository.findUserById(id);
    if (user == null) {
      return null;
    }
    return new UserDTO(user.getId(), user.getUsername(), user.getEmail());
  }

  public UserDTO getUserByUsername(@NotNull(message="username argument is required") String username) {
    User user = this.userRepository.findUserByUsername(username);
    if (user == null) {
      return null;
    }
    return new UserDTO(user.getId(), user.getUsername(), user.getEmail());
  }

  public UserDTO getUserByEmail(
    @NotNull(message="email argument is required") 
    @Email(message="Please provide a valid email address") 
    String email) {
    User user = this.userRepository.findUserByEmail(email);
    if (user == null) {
      return null;
    }
    return new UserDTO(user.getId(), user.getUsername(), user.getEmail());
  }

  public List<UserDTO> getAllUsers() {
    List<User> unsafe_users = this.userRepository.findAll();
    List<UserDTO> users = new ArrayList<>();

    for(User user: unsafe_users) {
      users.add(new UserDTO(user.getId(), user.getUsername(), user.getEmail()));
    }
    return users;
  }


  
  // public void updateUsername(@NotNull(message="User class argument is required") UserDTO user, @NotNull(message="Username argument is required.") String username) {
  //   // this.userRepository.updateUsername(user.getId(), username);
  // }
}
