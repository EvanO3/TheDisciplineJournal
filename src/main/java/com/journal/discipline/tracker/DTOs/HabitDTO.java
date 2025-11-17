package com.journal.discipline.tracker.DTOs;

import java.util.UUID;

import com.journal.discipline.tracker.Enums.HabitType;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HabitDTO {
    private UUID habitId;

    private String habitName;
  
    private String desc;
    private HabitType type;

    private String colour;

}
