package com.notesapp.controllers;
import org.apache.catalina.connector.Response;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.notesapp.entity.UserEntry;
import com.notesapp.repository.UserEntryRepository;
import com.notesapp.service.UserEntryService;

@RestController
@RequestMapping("/user")
public class UserController {

  @Autowired
  private UserEntryService userEntryService;

  @Autowired
  private UserEntryRepository userEntryRepository;

  @GetMapping
  public ResponseEntity<?> getAll() {
    List <UserEntry> all = userEntryService.getAll();
    if(all != null && !all.isEmpty()){
      return new ResponseEntity<>(all,HttpStatus.OK);
    }
     return new ResponseEntity<>(HttpStatus.NOT_FOUND);

  }

  @DeleteMapping
  public ResponseEntity<?> deleteById() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    userEntryRepository.deleteByUserName(authentication.getName());
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @PutMapping
  public ResponseEntity<?> updateUser(@RequestBody UserEntry myEntry) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();
    UserEntry old = userEntryService.FindByUserName(username);
    if (old != null) {
      old.setName(myEntry.getName() != null && !myEntry.getName().equals("") ? myEntry.getName() : old.getName());
      old.setAge(myEntry.getAge() != -1 ? myEntry.getAge() : old.getAge());
      old.setPassword(myEntry.getPassword() != null && !myEntry.getPassword().equals("") ? myEntry.getPassword() : old.getPassword());
      old.setUserName(myEntry.getUserName() != null && !myEntry.getUserName().equals("") ? myEntry.getUserName() : old.getUserName());
      userEntryService.saveNewUser(old);
      return new ResponseEntity<>(old, HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

}
