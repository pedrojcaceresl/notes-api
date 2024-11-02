package com.crud.basic.back.DTOs;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoteDTO {
    private Long id;

    @NotEmpty(message = "must not be empty")
    private String title;

    @NotEmpty(message = "must not be empty")
    private String content;

    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
