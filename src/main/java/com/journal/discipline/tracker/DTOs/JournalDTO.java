package com.journal.discipline.tracker.DTOs;

import java.time.LocalDate;

import java.util.UUID;


import com.journal.discipline.tracker.Enums.Emotion;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JournalDTO {
    private UUID journalId;
    private UUID userId;
    
    private LocalDate submissionDate;


    @NotBlank
    @Size(max=20)
    private String title;

    @NotBlank
    private String accomplishment;
    @NotBlank
    @NotNull
    private Emotion emotion;

    private int disciplineScore;
    
    @NotBlank
    @NotNull
    private String reflection;
}
