package com.crud.basic.back.services;

import com.crud.basic.back.DTOs.NoteDTO;
import com.crud.basic.back.Model.Note;
import com.crud.basic.back.Repository.NoteRepository;
import com.crud.basic.back.Services.NoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class NoteServiceTest {

    @Mock
    private NoteRepository noteRepository;

    @InjectMocks
    private NoteService noteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    void testFindAllNotes() {
        // Mock data
        NoteDTO note1 = new NoteDTO(1L, "Title 1", "Example content", LocalDateTime.now(), LocalDateTime.now());
        NoteDTO note2 = new NoteDTO(2L, "Title 2", "Example content 2", LocalDateTime.now(), LocalDateTime.now());
        List<NoteDTO> mockNotes = Arrays.asList(note1, note2);

        // Set Up mock behavior
        when(noteRepository.findAll());

        // Execute the service
        List<NoteDTO> notes = noteService.findAll();

        assertEquals(2, notes.size(), "Expected 2 notes in the result");
        assertEquals("Title 1", notes.get(0).getTitle());
        assertEquals("Title 2", notes.get(1).getTitle());

        assertEquals("Example content", notes.get(0).getContent());
        assertEquals("Example content 2", notes.get(1).getContent());

        verify(noteRepository, times(1)).findAll();
    }

    @Test
    void testFindNotesById() {
        Long noteId = 1L;

        Note note = new Note(noteId, "My note", "This is a note content", LocalDateTime.now(), LocalDateTime.now());

        when(noteRepository.findById(noteId)).thenReturn(Optional.<Note>of(note));

        // call the service
        Optional<NoteDTO> result = noteService.findById(noteId);

        // check result
        assertTrue(result.isPresent());
        assertEquals(note.getTitle(), result.get().getTitle());
        assertEquals(note.getContent(), result.get().getContent());

        // verify ID
        verify(noteRepository, times(1)).findById(noteId);
    }

    @Test
    void testSaveNote() {
        NoteDTO newNote = new NoteDTO(null, "My new title", "My new content", LocalDateTime.now(), LocalDateTime.now());
        Note savedNote = new Note(1L, "My new title", "My new content", LocalDateTime.now(), LocalDateTime.now());

        when(noteRepository.save(any(Note.class))).thenReturn(savedNote);

        NoteDTO result = noteService.save(newNote);

        // Verifications
        assertNotNull(result);
        assertEquals(savedNote.getId(), result.getId());
        assertEquals(savedNote.getTitle(), result.getTitle());
        assertEquals(savedNote.getContent(), result.getContent());

        verify(noteRepository, times(1)).save(any(Note.class));

    }

    @Test
    void testUpdateNote() {
        Long noteId = 1L;
        Note existingNote = new Note(noteId, "Old Title", "Old Content", LocalDateTime.now(), LocalDateTime.now());
        NoteDTO updatedNoteDTO = new NoteDTO(noteId, "Updated Title", "Updated Content", LocalDateTime.now(), LocalDateTime.now());

        when(noteRepository.findById(noteId)).thenReturn(Optional.of(existingNote));
        when(noteRepository.save(any(Note.class))).thenReturn(existingNote);

        NoteDTO result = noteService.update(noteId, updatedNoteDTO);

        assertNotNull(result);
        assertEquals(updatedNoteDTO.getTitle(), result.getTitle());
        assertEquals(updatedNoteDTO.getContent(), result.getContent());

        verify(noteRepository, times(1)).save(any(Note.class));
    }

    @Test
    void testDeleteById() {
        Long noteId = 1L;

        // Verify that the method is called
        noteService.deleteById(noteId);

        verify(noteRepository, times(1)).deleteById(noteId);
    }
}
