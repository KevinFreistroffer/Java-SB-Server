package com.example.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.api.model.User;
import com.example.api.model.UserDTO;
import java.util.List;
import org.springframework.lang.NonNull;

public interface UserRepository extends JpaRepository<User, Long> {
  User findUserById(Long id);
  User findUserByUsername(String username);
  User findUserByEmail(String email);
  @NonNull List<User> findAll();
}
