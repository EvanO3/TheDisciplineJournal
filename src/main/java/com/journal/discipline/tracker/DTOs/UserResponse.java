package com.journal.discipline.tracker.DTOs;

import java.util.List;
import java.util.Set;

import com.journal.discipline.tracker.Model.JournalEntry;

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
    private List<String>JournalTitle;
    
}
