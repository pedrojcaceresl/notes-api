package com.crud.basic.back.DTOs;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class NoteDTOTest {
    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidate() {
        NoteDTO note = new NoteDTO();
        note.setTitle("This is a title");
        note.setContent("This is a valid content");

        Set<ConstraintViolation<NoteDTO>> violations = validator.validate(note);
        assertTrue(violations.isEmpty(), "Expected no violations error for valid data");
    }

    @Test
    void testInvalidNoteTitleEmpty() {
        NoteDTO note = new NoteDTO();
        note.setTitle("");
        note.setContent("Valid Content");

        Set<ConstraintViolation<NoteDTO>> violations = validator.validate(note);
        assertFalse(violations.isEmpty(), "Expected validation error for empty title");

        boolean hasEmptyTitleError = violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("title") && v.getMessage().contains("must not be empty"));
        assertTrue(hasEmptyTitleError, "Expected 'title' field to have 'not empty constraint' violation");
    }

    @Test
    void testInvalidNoteContentEmpty() {
        NoteDTO note = new NoteDTO();
        note.setTitle("Valid Title");
        note.setContent("");

        Set<ConstraintViolation<NoteDTO>> violations = validator.validate(note);
        assertFalse(violations.isEmpty(), "Expected validation error for empty content");

        boolean hasEmptyContentError = violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("content") && v.getMessage().contains("must not be empty"));
        assertTrue(hasEmptyContentError, "Expected 'content' field to have 'not empty' constraint violation");
    }


    @Test
    void testValidDateFields() {
        NoteDTO note = new NoteDTO();
        note.setTitle("Valid Title");
        note.setContent("Valid Content");
        note.setCreatedDate(LocalDateTime.now());
        note.setUpdatedDate(LocalDateTime.now());
    }


}
