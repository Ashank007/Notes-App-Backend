package com.notesapp.controllers;
import org.apache.catalina.connector.Response;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.notesapp.service.UserEntryService;

@RestController
@RequestMapping("/user")
public class UserController {

  @Autowired
  private UserEntryService userEntryService;

  @GetMapping
  public ResponseEntity<?> getAll() {
    List <UserEntry> all = userEntryService.getAll();
    if(all != null && !all.isEmpty()){
      return new ResponseEntity<>(all,HttpStatus.OK);
    }
     return new ResponseEntity<>(HttpStatus.NOT_FOUND);

  }

  @PostMapping
  public ResponseEntity<UserEntry> createEntry(@RequestBody UserEntry myEntry) {
    try {
      myEntry.setDate(LocalDateTime.now());
      userEntryService.saveEntry(myEntry);
      return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping("id/{myId}")
  public ResponseEntity<UserEntry> getById(@PathVariable ObjectId myId) {
    Optional<UserEntry> userEntry = userEntryService.findById(myId);
    if (userEntry.isPresent()) {
      return new ResponseEntity<>(userEntry.get(), HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @DeleteMapping("id/{myId}")
  public ResponseEntity<?> deleteById(@PathVariable ObjectId myId) {
    userEntryService.DeleteById(myId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @PutMapping("/{userName}")
  public ResponseEntity<?> updateUser(@RequestBody UserEntry myEntry,@PathVariable String userName) {
    UserEntry old = userEntryService.FindByUserName(userName);
    if (old != null) {
      old.setName(myEntry.getName() != null && !myEntry.getName().equals("") ? myEntry.getName() : old.getName());
      old.setAge(myEntry.getAge() != -1 ? myEntry.getAge() : old.getAge());
      old.setPassword(myEntry.getPassword() != null && !myEntry.getPassword().equals("") ? myEntry.getPassword() : old.getPassword());
      old.setUserName(myEntry.getUserName() != null && !myEntry.getUserName().equals("") ? myEntry.getUserName() : old.getUserName());
      userEntryService.saveEntry(old);
      return new ResponseEntity<>(old, HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

}
