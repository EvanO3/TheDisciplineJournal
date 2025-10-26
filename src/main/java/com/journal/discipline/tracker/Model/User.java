package com.journal.discipline.tracker.Model;

import java.time.LocalDateTime;

import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID Id;
    private String username;
    private String password;
    private String email;

    @CreationTimestamp
    private LocalDateTime createdAt;



    private int streakCount;
    private int longestStreak;
    private double avgDisciplineScore;

    /*When the other models are created
     * Add validations after creation works
     * 
     */
    
    
}
