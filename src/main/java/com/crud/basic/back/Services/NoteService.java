package com.crud.basic.back.Services;

import com.crud.basic.back.DTOs.NoteDTO;
import com.crud.basic.back.Model.Note;
import com.crud.basic.back.Repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NoteService implements BaseService<NoteDTO, Long> {
    @Autowired
    private NoteRepository noteRepository;

    @Override
    public List<NoteDTO> findAll() {
        return noteRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<NoteDTO> findById(Long id) {
        return noteRepository.findById(id).map(this::convertToDTO);
    }

    @Override
    public NoteDTO save(NoteDTO noteDto) {
        Note note = new Note();

        note.setTitle(noteDto.getTitle());
        note.setContent(noteDto.getContent());
        note.setCreatedDate(LocalDateTime.now());
        note.setUpdatedDate(LocalDateTime.now());
        return convertToDTO(noteRepository.save(note));
    }

    @Override
    public NoteDTO update(Long id, NoteDTO entity) {
        return noteRepository.findById(id).map(note -> {
            note.setTitle(entity.getTitle());
            note.setContent(entity.getContent());
            note.setUpdatedDate(LocalDateTime.now());
            note.setCreatedDate(LocalDateTime.now());
            return convertToDTO(noteRepository.save(note));
        }).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        noteRepository.deleteById(id);
    }

    private NoteDTO convertToDTO(Note note) {
        NoteDTO dto = new NoteDTO();
        dto.setId(note.getId());
        dto.setTitle(note.getTitle());
        dto.setContent(note.getContent());
        dto.setCreatedDate(note.getCreatedDate());
        dto.setUpdatedDate(note.getUpdatedDate());
        return dto;
    }
}
