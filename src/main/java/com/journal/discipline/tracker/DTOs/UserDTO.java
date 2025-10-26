package com.journal.discipline.tracker.DTOs;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

  private UUID Id;

    private String username;
    @JsonIgnore
    private String password;

    private String email;

    private LocalDateTime createdAt;


    
    private int streakCount;

    private int longestStreak;

    private double avgDisciplineScore;
    
}
