package com.journal.discipline.tracker.Model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import com.journal.discipline.tracker.Enums.Emotion;


import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="journal")
public class JournalEntry {
    @Id
    @GeneratedValue(strategy=GenerationType.UUID)
    private UUID id;

    private LocalDate date; 

    private String title;
    
    private String accomplishment;

    private String reflection;
    
    private int disciplineScore;

    @Enumerated(EnumType.STRING)
    private Emotion emotion;
    
    @CreationTimestamp
    private LocalDateTime createdAt;
}
