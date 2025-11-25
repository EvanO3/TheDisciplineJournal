package com.journal.discipline.tracker.Controller;


import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.journal.discipline.tracker.Config.AppConstants;
import com.journal.discipline.tracker.DTOs.JournalDTO;
import com.journal.discipline.tracker.DTOs.JournalResponse;
import com.journal.discipline.tracker.Exceptions.ApiException;
import com.journal.discipline.tracker.Jwt.JwtUtils;

import com.journal.discipline.tracker.Service.JournalService;

import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/api")
public class JournalController {

    @Autowired
    private JournalService journalService;

    @Autowired
    private JwtUtils jwtUtils;

   
    
    

    @PostMapping("/public/journal")
    public ResponseEntity<JournalDTO> createJournalEntry(@RequestBody JournalDTO journalDTO, HttpServletRequest request) {
       UUID userId =  jwtUtils.provideUserIdFromRequest(request);
        JournalDTO savedEntry = journalService.createJournalEntry(journalDTO, userId);
        return new ResponseEntity<>(savedEntry, HttpStatus.CREATED);
  
    }
    
    /*Refactor to be journal Entry of the user */

    @GetMapping("/public/journal")
    public ResponseEntity<JournalResponse> getAllEntries(@RequestParam(defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
    @RequestParam(defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
    @RequestParam(defaultValue = AppConstants.SORT_JOURNAL_BY) String sortBy,
    @RequestParam(defaultValue = AppConstants.SORT_DIR) String sortOrder,
    HttpServletRequest request){
        UUID userId = jwtUtils.provideUserIdFromRequest(request);
        JournalResponse response = journalService.getAllJournalEntry(pageNumber, pageSize, sortBy, sortOrder, userId);

        if(response ==null){
              return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PutMapping("/public/journal/{journalId}")
    public ResponseEntity<JournalDTO> updateJournalEntry(@RequestBody JournalDTO journalEntry, @PathVariable UUID journalId, HttpServletRequest request){
        UUID userId = jwtUtils.provideUserIdFromRequest(request);
        JournalDTO response = journalService.updateJournalEntry(journalEntry,journalId,userId);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/public/journal/{journalId}")
     public ResponseEntity<JournalDTO> deleteEntry(@PathVariable UUID journalId, HttpServletRequest request){

        UUID userId = jwtUtils.provideUserIdFromRequest(request);
        JournalDTO response = journalService.deleteJournalEntry(journalId, userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }






  
}
