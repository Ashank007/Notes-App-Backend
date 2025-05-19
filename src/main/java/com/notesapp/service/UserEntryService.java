package com.notesapp.service;

import java.util.List;
import java.util.Optional;

import com.notesapp.entity.UserEntry;
import com.notesapp.repository.UserEntryRepository;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserEntryService{

  @Autowired
  private UserEntryRepository userEntryRepository;

  public void saveEntry(UserEntry userEntry) {
       userEntryRepository.save(userEntry);
  }
  public List <UserEntry> getAll() {
    return userEntryRepository.findAll();
  }

  public Optional<UserEntry> findById(ObjectId id) {
    return userEntryRepository.findById(id);
  }

  public void DeleteById(ObjectId id) {
     userEntryRepository.deleteById(id);
  }
  public UserEntry FindByUserName(String userName) {
    return userEntryRepository.findByUserName(userName);
  }

}


