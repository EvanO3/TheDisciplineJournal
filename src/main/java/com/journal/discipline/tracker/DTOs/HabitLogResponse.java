package com.journal.discipline.tracker.DTOs;

import java.time.LocalDate;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HabitLogResponse {
    private  HabitDTO habit;
    private boolean completionStatus;
    private LocalDate logDate;
}



