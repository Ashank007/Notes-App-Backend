package com.notesapp.controllers;

import org.apache.catalina.connector.Response;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import java.util.stream.Collectors;

import com.notesapp.entity.NotesEntry;
import com.notesapp.entity.UserEntry;
import com.notesapp.service.NotesEntryService;
import com.notesapp.service.UserEntryService;

@RestController
@RequestMapping("/notes")
public class NotesController {

  @Autowired
  private NotesEntryService notesEntryService;

  @Autowired
  private UserEntryService userEntryService;

  @GetMapping
  public ResponseEntity <?> getAllNotesOfUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();
    UserEntry user = userEntryService.FindByUserName(username);
    List <NotesEntry> all = user.getNotesEntries();
    if(all != null && !all.isEmpty()){
       return new ResponseEntity<>(all,HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @PostMapping
  public ResponseEntity <?> createNotes(@RequestBody NotesEntry note) {
    try {  
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();
    notesEntryService.saveEntry(note,username);
    return new ResponseEntity<>(note,HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping("id/{myId}")
  public ResponseEntity<?> getNotesById(@PathVariable ObjectId myId){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();
    UserEntry user = userEntryService.FindByUserName(username);
    List<NotesEntry> collect = user.getNotesEntries().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());
    if(!collect.isEmpty()){
      Optional<NotesEntry> notesEntry = notesEntryService.findById(myId);
      if(notesEntry.isPresent()){
      return new ResponseEntity<>(notesEntry.get(),HttpStatus.OK);
      }
    }
   return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @DeleteMapping("id/{myId}")
  public ResponseEntity<?> deleteNotesById(@PathVariable ObjectId myId) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();
    boolean removed = notesEntryService.DeleteById(myId,username);
    if(removed){
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @PutMapping("id/{myId}")
  public ResponseEntity<NotesEntry> updateById(@PathVariable ObjectId myId, @RequestBody NotesEntry notesEntry) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();
    UserEntry user = userEntryService.FindByUserName(username);
    List<NotesEntry> collect = user.getNotesEntries().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());
    if(!collect.isEmpty()){
      Optional<NotesEntry> oldnotesEntry = notesEntryService.findById(myId);
      if(oldnotesEntry.isPresent()){
      NotesEntry old = oldnotesEntry.get();
      old.setTitle(notesEntry.getTitle() != null && !notesEntry.getTitle().equals("") ? notesEntry.getTitle() : old.getTitle());
      old.setDescription(notesEntry.getDescription() != null && !notesEntry.getDescription().equals("") ? notesEntry.getDescription() : old.getDescription());
      notesEntryService.saveEntry(old);
      return new ResponseEntity<>(old, HttpStatus.OK);
      }
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }


}
