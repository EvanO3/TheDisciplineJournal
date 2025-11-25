package com.journal.discipline.tracker.DTOs;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HabitLogDTO {
    

    private UUID Id;

    @CreationTimestamp
    private LocalDateTime createdAt;


    private LocalDate logDate;

    
    private boolean completionStatus;
}
