package com.journal.discipline.tracker.DTOs;

import java.util.UUID;

import com.journal.discipline.tracker.Enums.HabitType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HabitDTO {
    private UUID habitId;

  
    @NotBlank
    private String habitName;
  
    @NotBlank
    private String desc;
    @NotBlank
    private HabitType type;

    @NotBlank
    private String colour;

}
