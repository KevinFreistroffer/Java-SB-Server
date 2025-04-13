package com.example.api.service;

import com.example.api.model.NewUser;
import com.example.api.model.UpdateUserRequest;
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

  public UserDTO getUserById(@NotNull(message="id argument is required") Long id) {
    User user = this.userRepository.findUserById(id);
    if (user == null) {
      return null;
    }
    return new UserDTO(user.getId(), user.getUsername(), user.getEmail(), user.getFirstName(), user.getLastName());
  }

  public UserDTO getUserByUsername(@NotNull(message="username argument is required") String username) {
    User user = this.userRepository.findUserByUsername(username);
    if (user == null) {
      return null;
    }
    return new UserDTO(user.getId(), user.getUsername(), user.getEmail(), user.getFirstName(), user.getLastName());
  }

  public UserDTO getUserByEmail(
    @NotNull(message="email argument is required") 
    @Email(message="Please provide a valid email address") 
    String email) {
    User user = this.userRepository.findUserByEmail(email);
    if (user == null) {
      return null;
    }
    return new UserDTO(user.getId(), user.getUsername(), user.getEmail(), user.getFirstName(), user.getLastName());
  }

  public List<UserDTO> getAllUsers() {
    List<User> unsafe_users = this.userRepository.findAll();
    List<UserDTO> users = new ArrayList<>();

    for(User user: unsafe_users) {
      users.add(new UserDTO(user.getId(), user.getUsername(), user.getEmail(), user.getFirstName(), user.getLastName()));
    }
    return users;
  }
 
  public Boolean isEmailAvailable(@NotNull(message="Email argument is required") String email) {
    User existingUser = this.userRepository.findUserByEmail(email);

    return existingUser == null;
  }

  public Boolean isUsernameAvailable(@NotNull(message="Username argument is required") String username) {
    User existingUser = this.userRepository.findUserByUsername(username);

    return existingUser == null;
  }

  public UserDTO createUser(NewUser newUser) {
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

    User user = new User(newUser.getUsername(), newUser.getEmail(), hashed_password);
    
    this.userRepository.save(user);
    UserDTO userDTO = new UserDTO(user.getId(), user.getUsername(), user.getEmail(), user.getFirstName(), user.getLastName());
    return userDTO;
  }

  public UserDTO updatePassword(Long userId, String password) {
    // Check if user exists
    User existingUser = this.userRepository.findUserById(userId);
    if (existingUser == null) {
      throw new IllegalArgumentException("User does not exist.");
    }

    String hashed_password = utils.hashPassword(password);
    existingUser.setHashedPasword(hashed_password);
    User updatedUser = this.userRepository.save(existingUser);

    return new UserDTO(updatedUser.getId(), updatedUser.getUsername(), updatedUser.getEmail(), updatedUser.getFirstName(), updatedUser.getLastName());
  }
  
  public UserDTO updateUser(Long userId, UpdateUserRequest updateRequest) {
    // Check if user exists
    User existingUser = this.userRepository.findUserById(userId);
    if (existingUser == null) {
      throw new IllegalArgumentException("User does not exist.");
    }

    if (updateRequest.getPassword() != null) {
      String hashed_password = utils.hashPassword(updateRequest.getPassword());
      existingUser.setHashedPasword(hashed_password);
    }
    
    if (updateRequest.getFirstName() != null) {
      existingUser.setFirstName(updateRequest.getFirstName());
    }
    
    if (updateRequest.getLastName() != null) {
      existingUser.setLastName(updateRequest.getLastName());
    }

    User updatedUser = this.userRepository.save(existingUser);
    return new UserDTO(updatedUser.getId(), updatedUser.getUsername(), updatedUser.getEmail(), 
                      updatedUser.getFirstName(), updatedUser.getLastName());
  }

  // public void updateUsername(@NotNull(message="User class argument is required") UserDTO user, @NotNull(message="Username argument is required.") String username) {
  //   // this.userRepository.updateUsername(user.getId(), username);
  // }
}
