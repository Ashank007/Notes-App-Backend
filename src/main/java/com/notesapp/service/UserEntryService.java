package com.notesapp.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.notesapp.entity.UserEntry;
import com.notesapp.repository.UserEntryRepository;

import org.bson.types.ObjectId;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserEntryService{

  @Autowired
  private UserEntryRepository userEntryRepository;

  private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  public void saveEntry(UserEntry userEntry) {
       userEntryRepository.save(userEntry);
  }

  private static final Logger logger = LoggerFactory.getLogger(UserEntryService.class);

  public boolean saveNewUser(UserEntry userEntry) {
    try {
       userEntry.setPassword(passwordEncoder.encode(userEntry.getPassword()));
       userEntry.setRoles(Arrays.asList("USER"));
       userEntryRepository.save(userEntry);
       return true;
    } catch (Exception e) {
      logger.info("Nahi Bana User");
      return false;
    }

  }

  public void saveAdmin(UserEntry userEntry) {
       userEntry.setPassword(passwordEncoder.encode(userEntry.getPassword()));
       userEntry.setRoles(Arrays.asList("USER","ADMIN"));
       userEntryRepository.save(userEntry);
  }

  public void saveUser(UserEntry userEntry) {
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


