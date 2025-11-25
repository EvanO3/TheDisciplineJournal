package com.journal.discipline.tracker.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.journal.discipline.tracker.Model.JournalEntry;

@Repository
public interface JournalRepository extends JpaRepository<JournalEntry, UUID> {

    Optional<JournalEntry> findByUserIdAndTitle(UUID userId, String title);
    List<JournalEntry> findByUserId(UUID userId);
    Optional<JournalEntry> findByUserIdAndSubmissionDate(UUID userId, LocalDate now);
    Page<JournalEntry> findAllByUserId(PageRequest pageDetails, UUID userId);



}
