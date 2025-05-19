package com.notesapp.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Document(collection = "notes")
@Data
@NoArgsConstructor
@Getter
@Setter
public class NotesEntry {

  @Id
  private ObjectId id;
  @NonNull
  private String title;
  @NonNull
  private String description;

}
