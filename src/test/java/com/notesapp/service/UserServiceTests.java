package com.notesapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.notesapp.repository.UserEntryRepository;

import org.springframework.boot.test.context.SpringBootTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootTest
public class UserServiceTests {

  @Autowired
  private UserEntryRepository userEntryRepository;

  @Test
  public void testFindByUserName() {
    assertNotNull(userEntryRepository.findByUserName("Sumit"));
  }
}
