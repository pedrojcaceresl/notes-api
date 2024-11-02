package com.crud.basic.back.Controllers;

import com.crud.basic.back.DTOs.NoteDTO;
import com.crud.basic.back.Response.ApiResponse;
import com.crud.basic.back.Services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/api/notes")
public class NoteController implements BaseController<NoteDTO, Long> {
    @Autowired
    private NoteService noteService;


    @Override
    public ResponseEntity<ApiResponse<List<NoteDTO>>> getAll() {
        List<NoteDTO> notes = noteService.findAll();
        return ResponseEntity.ok(new ApiResponse<List<NoteDTO>>(notes, "Notas obtenidas con exito!", true, HttpStatus.OK.value()));
    }

    @Override
    public ResponseEntity<ApiResponse<NoteDTO>> getById(@PathVariable Long id) {
        Optional<NoteDTO> note = noteService.findById(id);
        if (note.isPresent()) {
            return ResponseEntity.ok(new ApiResponse<>(note.get(), "Nota obtenida con éxito!", true, HttpStatus.OK.value()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(null, "Nota no encontrada", false, HttpStatus.NOT_FOUND.value()));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<NoteDTO>> create(@RequestBody NoteDTO dto) {
        NoteDTO createdNote = noteService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(createdNote, "Nota creada con éxito!", true, HttpStatus.CREATED.value()));
    }

    @Override
    public ResponseEntity<ApiResponse<NoteDTO>> update(@PathVariable Long id, @RequestBody NoteDTO dto) {
        Optional<NoteDTO> existingNote = noteService.findById(id);
        if (existingNote.isPresent()) {
            NoteDTO updatedNote = noteService.update(id, dto);
            return ResponseEntity.ok(new ApiResponse<>(updatedNote, "Nota actualizada con éxito!", true, HttpStatus.OK.value()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(null, "Nota no encontrada", false, HttpStatus.NOT_FOUND.value()));
        }
    }

    @Override
    public ResponseEntity<ApiResponse<NoteDTO>> deleteById(@PathVariable Long id) {
        Optional<NoteDTO> existingNote = noteService.findById(id);
        if (existingNote.isPresent()) {
            noteService.deleteById(id);
            return ResponseEntity.ok(new ApiResponse<>(null, "Nota eliminada con éxito!", true, HttpStatus.OK.value()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(null, "Nota no encontrada", false, HttpStatus.NOT_FOUND.value()));
        }
    }

}
