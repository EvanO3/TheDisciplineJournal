package com.journal.discipline.tracker.Model;

import java.util.UUID;

import com.journal.discipline.tracker.Enums.HabitType;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name="habit")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Habit {
    
    @Column(name="habit_id")
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID habitId;

   
    private String habitName;
    @Column(name = "description")
    private String desc;

    @Enumerated(EnumType.STRING)
    private HabitType type;

    private String colour;


    @ManyToOne
    @JoinColumn(name ="user_id")
    private User user;

}
