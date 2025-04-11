package com.example.api.service;

import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;

class Journal {
  String title;
  LocalDateTime createdAt;
  LocalDateTime updatedAt;
  String category;

  public Journal(String title, String category, LocalDateTime createdAt, LocalDateTime updatedAt) {
    this.title = title;
    this.category = category;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }
}


@Service
public class JournalService {
  private final UserService userService;

  JournalService(UserService userService) {
    this.userService = userService;
  }

  public Journal[] getJournals(Integer userId) {
    Journal j1 = new Journal("Title", "Category", LocalDateTime.now(), LocalDateTime.now());

    return new Journal[] { j1 };
  }

  public Journal createJournal(String title, String category) {
    return new Journal(title, category, LocalDateTime.now(), LocalDateTime.now());
  }
}
