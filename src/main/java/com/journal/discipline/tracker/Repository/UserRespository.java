package com.journal.discipline.tracker.Repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.journal.discipline.tracker.Model.JournalEntry;
import com.journal.discipline.tracker.Model.User;

@Repository
public interface UserRespository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);
 
        
}
