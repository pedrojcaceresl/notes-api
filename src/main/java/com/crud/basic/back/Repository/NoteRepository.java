package com.crud.basic.back.Repository;

import com.crud.basic.back.Model.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long> {
}
