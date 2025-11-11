package com.journal.discipline.tracker.Model;

import java.util.UUID;

import com.journal.discipline.tracker.Enums.HabitType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="habit")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Habit {
    
    @Column(name="habit_id")
    private UUID habitId;
    
    private String habitName;
    @Column(name = "description")
    private String desc;

    @Enumerated(EnumType.STRING)
    private HabitType type;

    private String colour;


}
