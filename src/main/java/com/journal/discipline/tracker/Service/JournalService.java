package com.journal.discipline.tracker.Service;

import java.util.UUID;

import com.journal.discipline.tracker.DTOs.JournalDTO;
import com.journal.discipline.tracker.DTOs.JournalResponse;

public interface JournalService {
    /*Defining all operations and return types here */
    JournalDTO createJournalEntry(JournalDTO journalDTO);
    JournalResponse getAllJournalEntry();
    JournalDTO updateJournalEntry(JournalDTO journalDTO, UUID id);
    JournalDTO deleteJournalEntry(UUID id);
}
