package com.journal.discipline.tracker.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.journal.discipline.tracker.DTOs.CompletionStatusDTO;
import com.journal.discipline.tracker.Model.HabitLog;

@Repository
public interface HabitLogRepository extends JpaRepository<HabitLog, UUID>  {

    List<HabitLog> findAllByUserIdAndLogDate(UUID userId, LocalDate logDate);
    @Query(value="Select * from habit_log h  where h.habit_id = :habitId  AND h.user_id =:userId AND h.log_date = :logDate", nativeQuery =true)
    Optional<HabitLog> findByHabitIdAndUserIdAndLogDate(@Param("userId")UUID userId, @Param("habitId") UUID habitId, @Param("logDate")LocalDate logDate);
    List<HabitLog>findByUserIdAndLogDateBetween(UUID UserId, LocalDate startDate, LocalDate endDate);

    @Query(value="Select COUNT(*) habit_log, Sum(case when COMPLETION_STATUS  = true then 1 else 0 end) from habit_log  where user_Id =:userId and log_date =:logDate", nativeQuery = true)    
    CompletionStatusDTO findCompletionStatus(@Param("userId") UUID userId, @Param("logDate") LocalDate logDate);
} 


/*
 * 
 */
