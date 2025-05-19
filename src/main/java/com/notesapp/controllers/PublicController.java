package com.notesapp.controllers;
import java.time.LocalDateTime;

import com.notesapp.entity.UserEntry;
import com.notesapp.service.UserEntryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class PublicController {

  @Autowired
  private UserEntryService userEntryService;

  @PostMapping("/create-user")
  public ResponseEntity<UserEntry> createEntry(@RequestBody UserEntry myEntry) {
    try {
      myEntry.setDate(LocalDateTime.now());
      userEntryService.saveNewUser(myEntry);
      return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping("/health-check")
  public String healthcheck() {
    return "Server is Working Fine";
  }

}
