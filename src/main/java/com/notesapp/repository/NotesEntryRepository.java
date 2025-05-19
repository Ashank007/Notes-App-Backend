
package com.notesapp.repository;

import com.notesapp.entity.NotesEntry;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotesEntryRepository extends MongoRepository<NotesEntry,ObjectId>{


}
