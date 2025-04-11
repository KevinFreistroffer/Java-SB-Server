package com.example.api.controller;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.api.model.User;
import com.example.api.model.UserDTO;
import com.example.api.ApiApplication;
import com.example.api.model.NewUser;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import jakarta.validation.Valid;
import com.example.api.service.UserService;
import java.util.List;

@RestController
public class UserController {

  private final ApiApplication apiApplication;
  private final UserService userService;
  private final AtomicLong id = new AtomicLong();
  private String username = "";
  private String email = "";


  public UserController(UserService userService, ApiApplication apiApplication) {
    this.userService = userService;
    this.apiApplication = apiApplication;
  }

  @GetMapping("/user")
  public ResponseEntity<?> getUser(
    @RequestParam(value="id", required=false) Long id,
    @RequestParam(value="username", required=false) String username,
    @RequestParam(value="email", required=false) String email
  ) {
    if (id == null && username == null && email == null) {
      return ResponseEntity.badRequest().body("Missing required request parameters.");
    }

    // All rparams are accounted for. Can try to get the user. If the db returns no user, than return error or similar.
    UserDTO user = null;
    if (id != null) {
      user = this.userService.getUserById(id);
    } else if (username != null) {
      user = this.userService.getUserByUsername(username);
    } else if (email != null) {
      user = this.userService.getUserByEmail(email);
    }

    if (user == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
    }

    return ResponseEntity.ok(user);
  }

  @GetMapping("/users")
  public ResponseEntity<?> getAllUsers() {
    try {
      List<UserDTO> users = userService.getAllUsers();
      return ResponseEntity.ok(users);
    } catch (Exception e) {
      System.out.println("Errorz " + e.getMessage());
      System.out.println("Errorz " + e.getMessage());
      System.out.println("Errorz " + e.getMessage());
      System.out.println("Errorz " + e.getMessage());
      System.out.println("Errorz " + e.getMessage());
      System.out.println("Errorz " + e.getMessage());
      return ResponseEntity.badRequest().body("Could not get users. Error: " + e.getMessage());
    }
  }

  @PostMapping(path="/user")
  public ResponseEntity<?> createUser(@Valid @RequestBody NewUser newUser) {
    try {
      UserDTO user = userService.createUser(newUser);
      return ResponseEntity.status(HttpStatus.CREATED).body(user);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Error creating a user: " + e.getMessage());
    }
  }
}

