package com.notesapp.controllers;

import java.util.List;

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
@RequestMapping("/admin")
public class AdminController {

  @Autowired
  private UserEntryService userService;

  @GetMapping("/all-users")
  public ResponseEntity<?> getAllUsers() {
    List<UserEntry> all = userService.getAll();
    if (all != null && !all.isEmpty()) {
      return new ResponseEntity<>(all, HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @PostMapping("/create-admin-user")
  public void createUser(@RequestBody UserEntry user) {
    userService.saveAdmin(user);
  }

}
