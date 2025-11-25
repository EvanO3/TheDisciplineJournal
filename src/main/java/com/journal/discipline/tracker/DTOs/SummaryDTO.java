package com.journal.discipline.tracker.DTOs;

import java.util.List;

import com.journal.discipline.tracker.Model.JournalEntry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * - Daily summary should return all the information to the user
    - The Habit log for the day
    - Their Score i.e discipline score, streak
    - Journal Entry for the day
 * 
 * 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SummaryDTO {
    private StreakData streakData;
    private JournalDTO journalEntry;
    private List<HabitLogResponse> habitLog;

}
