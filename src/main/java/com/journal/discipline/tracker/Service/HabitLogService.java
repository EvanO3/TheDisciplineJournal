package com.journal.discipline.tracker.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.journal.discipline.tracker.DTOs.HabitLogDTO;
import com.journal.discipline.tracker.DTOs.HabitLogResponse;

/*
 * CREATE a log (user marks a habit as completed)
 *

READ logs (today, past week, month, etc.)

UPDATE only in rare cases
(e.g., user toggles completion the same day)




 /*Completed  */


    



public interface HabitLogService {
    public HabitLogDTO createLog(UUID userId, UUID habitId, LocalDate logDate);
    public HabitLogResponse updateCompletionStatus(UUID userId, UUID habitLogId);
    public List<HabitLogResponse> getDailyHabitLog(UUID userId);
    
} 
