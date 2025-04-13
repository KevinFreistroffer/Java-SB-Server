package com.example.api.controller;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.api.model.UserDTO;
import com.example.api.ApiApplication;
import com.example.api.model.NewUser;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import jakarta.validation.Valid;
import com.example.api.service.UserService;
import java.util.List;
import com.example.api.defs.http_responses.user.ApiResponse;
import org.springframework.web.bind.annotation.*;

// public class ConvertToString {
//   public ConvertToString() {}

//   public String convert(Integer value) {
//     return value.toString();
//   }
// }

@RestController
// @RequestMapping("/api")
public class UserController {

  // private final ApiApplication apiApplication;
  private final UserService userService;
  // private final AtomicLong id = new AtomicLong();
  // private String username = "";
  // private String email = "";


  public UserController(UserService userService, ApiApplication apiApplication) {
    this.userService = userService;
    // this.apiApplication = apiApplication;
  }

  @GetMapping(value="/user", produces="application/json")
  public ResponseEntity<ApiResponse<UserDTO>> getUser(
    @RequestParam(value="id", required=false) Long id,
    @RequestParam(value="username", required=false) String username,
    @RequestParam(value="email", required=false) String email
  ) {
    try {
      if (id == null && username == null && email == null) {
        return ResponseEntity.badRequest().body(ApiResponse.error("Missing required request parameters."));
      }
  

      UserDTO user = null;
      if (id != null) {
        user = this.userService.getUserById(id);
      } else if (username != null) {
        user = this.userService.getUserByUsername(username);
      } else if (email != null) {
        user = this.userService.getUserByEmail(email);
      }
  
      if (user == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error("User not found."));
      }
  
      return ResponseEntity.status(HttpStatus.OK.value()).body(ApiResponse.success(user));
    } catch(Exception e) {
      System.out.println("An error occurred getting a user: " + e.getMessage());
      return  ResponseEntity.badRequest().body(ApiResponse.error("Error: could not fetch user."));
    }
  }

  @GetMapping("/users")
  public ResponseEntity<ApiResponse<List<UserDTO>>> getAllUsers() {
    try {
      List<UserDTO> users = userService.getAllUsers();
      return ResponseEntity.status(HttpStatus.OK.value()).body(ApiResponse.success(users));
    } catch (Exception e) {
      return ResponseEntity.badRequest()
        .body(ApiResponse.error("Error getting users: " + e.getMessage()));
    }
  }

  @PostMapping("/users")
  public ResponseEntity<ApiResponse<UserDTO>> createUser(@RequestBody NewUser user) {
    try {
      UserDTO createdUser = userService.createUser(user);
      return ResponseEntity.status(HttpStatus.CREATED.value())
        .body(ApiResponse.success(createdUser, "User created successfully"));
    } catch (Exception e) {
      return ResponseEntity.badRequest()
        .body(ApiResponse.error(e.getMessage()));
    }
  }

  @GetMapping("/email-available")
  public ResponseEntity<ApiResponse<Boolean>> emailAvailable(@RequestParam(value="email") String email) {
    
    return ResponseEntity.status(HttpStatus.OK.value()).body(ApiResponse.success(true));
  }
}

