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

  @GetMapping("{userName}")
  public ResponseEntity <?> getAllNotesOfUser(@PathVariable String userName) {
    UserEntry user = userEntryService.FindByUserName(userName);
    List <NotesEntry> all = user.getNotesEntries();
    if(all != null && !all.isEmpty()){
       return new ResponseEntity<>(all,HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @PostMapping("{userName}")
  public ResponseEntity <?> createNotes(@RequestBody NotesEntry note,@PathVariable String userName) {
    try {  
    notesEntryService.saveEntry(note,userName);
    return new ResponseEntity<>(note,HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  @DeleteMapping("id/{userName}/{myId}")
  public ResponseEntity<?> deleteNotesById(@PathVariable ObjectId myId,@PathVariable String userName) {
    notesEntryService.DeleteById(myId,userName);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @PutMapping("id/{userName}/{myId}")
  public ResponseEntity<NotesEntry> updateById(@PathVariable ObjectId myId, @RequestBody NotesEntry notesEntry,@PathVariable String userName) {
    NotesEntry old = notesEntryService.findById(myId).orElse(null);
    if (old != null) {
      old.setTitle(notesEntry.getTitle() != null && !notesEntry.getTitle().equals("") ? notesEntry.getTitle() : old.getTitle());
      old.setDescription(notesEntry.getDescription() != null && !notesEntry.getDescription().equals("") ? notesEntry.getDescription() : old.getDescription());
      notesEntryService.saveEntry(old);
      return new ResponseEntity<>(old, HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

}
