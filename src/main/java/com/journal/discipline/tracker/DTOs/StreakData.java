package com.journal.discipline.tracker.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StreakData {
    private int streakCount;
    private int longestStreak;
    private double avgDisciplineScore;



    
}
