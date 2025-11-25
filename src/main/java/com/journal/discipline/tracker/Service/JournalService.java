package com.journal.discipline.tracker.Service;

import java.util.UUID;

import com.journal.discipline.tracker.DTOs.JournalDTO;
import com.journal.discipline.tracker.DTOs.JournalResponse;

public interface JournalService {
    /*Defining all operations and return types here */
    JournalDTO createJournalEntry(JournalDTO journalDTO, UUID userId);
    JournalResponse getAllJournalEntry(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder, UUID userId);
    JournalDTO updateJournalEntry(JournalDTO journalDTO, UUID journalId, UUID userId);
    JournalDTO deleteJournalEntry(UUID id, UUID userId);
}
