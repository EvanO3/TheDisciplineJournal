package com.journal.discipline.tracker.Repository;

import java.util.List;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.journal.discipline.tracker.Model.Habit;

@Repository
public interface HabitRepository extends JpaRepository<Habit, UUID> {

    Habit findByHabitName(String habitName);
    List<Habit> findHabitsByUserId(UUID id);
    

}
