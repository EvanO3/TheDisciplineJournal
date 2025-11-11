package com.journal.discipline.tracker.DTOs;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.journal.discipline.tracker.Model.JournalEntry;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

  private UUID Id;

    private String username;



    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)

    @Pattern(regexp ="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,30}$",
    message = "Password must be at least 8 characters long, include 1 uppercase, 1 lowercase, 1 number, 1 special character, and contain no spaces")
    private String password;

    private String email;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    
    private int streakCount;

    private int longestStreak;

    private double avgDisciplineScore;
    

}
