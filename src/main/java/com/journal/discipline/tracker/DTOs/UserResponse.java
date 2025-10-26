package com.journal.discipline.tracker.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserResponse {
    private String username;
    private int streakCount;
    private int longestStreak;
    private double avgDisciplineScore;

    
}
