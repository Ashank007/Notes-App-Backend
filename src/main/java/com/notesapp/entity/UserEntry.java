package com.notesapp.entity;

import com.notesapp.entity.NotesEntry;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Document(collection = "users")
@Getter
@Setter
public class UserEntry {

  @Id
  private ObjectId id;
  private String name;
  @Indexed(unique = true)
  @NonNull
  private String userName;
  private int age;
  private String password;
  private LocalDateTime date;

  @DBRef
  private List <NotesEntry> notesEntries = new ArrayList<>();
}
