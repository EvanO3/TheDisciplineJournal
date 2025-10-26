package com.journal.discipline.tracker.DTOs;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.journal.discipline.tracker.Enums.Emotion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JournalDTO {
    private UUID journalId;
    private LocalDate date;
    private String title;
    private String accomplishment;
    private Emotion emotion;
    private int disciplineScore;
    private String reflection;
}
