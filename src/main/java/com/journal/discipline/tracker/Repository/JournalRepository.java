package com.journal.discipline.tracker.Repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.journal.discipline.tracker.Model.JournalEntry;

@Repository
public interface JournalRepository extends JpaRepository<JournalEntry, UUID> {

    JournalEntry findByTitle(String title);
    
}
