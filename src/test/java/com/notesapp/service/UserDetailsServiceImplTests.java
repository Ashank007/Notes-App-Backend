package com.notesapp.service;

import com.notesapp.entity.UserEntry;
import com.notesapp.repository.UserEntryRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

@SpringBootTest
class UserDetailsServiceImplTests {

  @InjectMocks
  private UserDetailsServiceImpl userDetailsServiceImpl;

  @Mock
  private UserEntryRepository userEntryRepository;

  @BeforeEach
  void setUp(){
    MockitoAnnotations.initMocks(this);
  }

  @Test
  void loadUserByUsernameTest(){
    UserEntry mockUserEntry = new UserEntry();
    mockUserEntry.setUserName("ram");
    mockUserEntry.setPassword("testing");
    mockUserEntry.setRoles(new ArrayList<>());

    when(userEntryRepository.findByUserName(ArgumentMatchers.anyString())).thenReturn(mockUserEntry);
    UserDetails user =  userDetailsServiceImpl.loadUserByUsername("ram");
    assertNotNull(user);
  }

}
