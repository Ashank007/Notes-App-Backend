package com.notesapp.service;

import java.util.List;
import java.util.Optional;

import com.notesapp.entity.NotesEntry;
import com.notesapp.entity.UserEntry;
import com.notesapp.repository.NotesEntryRepository;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class NotesEntryService{

  @Autowired
  private NotesEntryRepository notesEntryrepository;

  @Autowired
  private UserEntryService userEntryService;

  @Transactional
  public void saveEntry(NotesEntry notesEntry,String userName) {
    UserEntry user = userEntryService.FindByUserName(userName);   
    NotesEntry saved = notesEntryrepository.save(notesEntry);
    user.getNotesEntries().add(saved);
    userEntryService.saveUser(user);
  }
  public void saveEntry(NotesEntry notesEntry) {
    notesEntryrepository.save(notesEntry);
  }
  public List <NotesEntry> getAll() {
    return notesEntryrepository.findAll();
  }

  public Optional<NotesEntry> findById(ObjectId id) {
    return notesEntryrepository.findById(id);
  }

  @Transactional
  public boolean DeleteById(ObjectId id,String userName) {
    boolean removed = false;
    try {
     UserEntry user = userEntryService.FindByUserName(userName);
     removed = user.getNotesEntries().removeIf(x -> x.getId().equals(id));
     if(removed){
     userEntryService.saveUser(user);
     notesEntryrepository.deleteById(id);
     }
    } catch (Exception e) {
      System.out.println(e);
      throw new RuntimeException("An Error occurred while deleting the notes. ",e);
    }
    return removed;
  }

}


