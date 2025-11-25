package com.journal.discipline.tracker.DTOs;

import java.time.LocalDateTime;

import java.util.UUID;


import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

  private UUID Id;

   @NotBlank(message="Username is required")
  @Size(min=2, max =15, message="Username must be between 2 and 15 chars")
    private String username;



    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)

    @Pattern(regexp ="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,30}$",
    message = "Password must be at least 8 characters long, include 1 uppercase, 1 lowercase, 1 number, 1 special character, and contain no spaces")
    private String password;

    @NotBlank(message="Email cannot be blank")
    @Email(message="a valid email must be provided")
    private String email;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    
    private int streakCount;

    private int longestStreak;

    private double avgDisciplineScore;
    

}
