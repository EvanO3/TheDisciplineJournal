package com.journal.discipline.tracker.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.journal.discipline.tracker.Model.JournalEntry;

@Repository
public interface JournalRepository extends JpaRepository<JournalEntry, UUID> {

    Optional<JournalEntry> findByUserIdAndTitle(UUID userId, String title);
    List<JournalEntry> findByUserId(UUID userId);



}
