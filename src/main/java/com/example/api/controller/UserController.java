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
import com.example.api.model.UpdateUserRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import com.example.api.service.UserService;
import java.util.List;
import java.lang.Long;
import com.example.api.defs.http_responses.user.ApiResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import com.example.api.config.SecurityConfig;

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
  @PreAuthorize("hasRole(ROLE_ADMIN)")
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
  @PreAuthorize("hasRole(ROLE_ADMIN)")
  public ResponseEntity<ApiResponse<List<UserDTO>>> getAllUsers() {
    try {
      List<UserDTO> users = userService.getAllUsers();
      return ResponseEntity.status(HttpStatus.OK.value()).body(ApiResponse.success(users));
    } catch (Exception e) {
      return ResponseEntity.badRequest()
        .body(ApiResponse.error("Error getting users: " + e.getMessage()));
    }
  }

  @GetMapping("/email-available")
  @PreAuthorize("hasRole(ROLE_ADMIN)")
  public ResponseEntity<ApiResponse<Boolean>> emailAvailable(@RequestParam(value="email") String email) {
    try {
      Boolean emailIsAvailable = userService.isEmailAvailable(email);

      return ResponseEntity.status(HttpStatus.OK.value()).body(ApiResponse.success(emailIsAvailable));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
    }
  }

  @GetMapping("/username-available")
  @PreAuthorize("hasRole(ROLE_ADMIN)")
  public ResponseEntity<ApiResponse<Boolean>> usernameAvailable(@RequestParam(value="username") String username) {
    try {
      Boolean usernameIsAvailable = userService.isUsernameAvailable(username);

      return ResponseEntity.status(HttpStatus.OK.value()).body(ApiResponse.success(usernameIsAvailable));
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
    }
  }

  @PostMapping("/user")
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

  @PutMapping("/user")
  // @PreAuthorize("@customAuthorization.isAuthorized()")
  public ResponseEntity<ApiResponse<UserDTO>> updateUser(
    @RequestBody UpdateUserRequest updateRequest) {
    try {
      if (updateRequest == null || (updateRequest.getPassword() == null && updateRequest.getFirstName() == null && updateRequest.getLastName() == null)) {
        return ResponseEntity.badRequest().body(ApiResponse.error("At least 1 argument to update is required in the request."));
      }
      
      UserDTO updatedUser = userService.updateUser(updateRequest.getUserId(), updateRequest);
      return ResponseEntity.status(HttpStatus.CREATED.value()).body(ApiResponse.success(updatedUser, "User updated successfully"));
    } catch (Exception e) {
      return ResponseEntity.badRequest()
        .body(ApiResponse.error(e.getMessage()));
    }
  }
}

