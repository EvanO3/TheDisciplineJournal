package com.journal.discipline.tracker.Repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.journal.discipline.tracker.DTOs.StreakData;

import com.journal.discipline.tracker.Model.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);
    
    @Query(value="SELECT Longest_Streak, Streak_count , Avg_Discipline_Score from USERS where id = :userId", nativeQuery =true)
    StreakData findStreakDataById(@Param("userId") UUID userId);
        
}
