package com.journal.discipline.tracker.Controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.journal.discipline.tracker.DTOs.JournalDTO;
import com.journal.discipline.tracker.DTOs.JournalResponse;
import com.journal.discipline.tracker.Model.JournalEntry;
import com.journal.discipline.tracker.Service.JournalService;


@RestController
@RequestMapping("/api")
public class JournalController {

    @Autowired
    private JournalService journalService;

    @PostMapping("/public/journal")
    public ResponseEntity<JournalDTO> createJournalEntry(@RequestBody JournalDTO journalDTO) {
        JournalDTO savedEntry = journalService.createJournalEntry(journalDTO);
        return new ResponseEntity<>(savedEntry, HttpStatus.CREATED);
  
    }

    @GetMapping("/public/journal")
    public ResponseEntity<JournalResponse> getAllEntries(){
        JournalResponse response = journalService.getAllJournalEntry();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PutMapping("/public/journal/{Id}")
    public ResponseEntity<JournalDTO> updateJournalEntry(@RequestBody JournalDTO journalEntry, @PathVariable("Id") UUID Id){
        JournalDTO response = journalService.updateJournalEntry(journalEntry, Id);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/public/journal/{Id}")
     public ResponseEntity<JournalDTO> deleteEntry(@PathVariable("Id") UUID Id){
        JournalDTO response = journalService.deleteJournalEntry(Id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
