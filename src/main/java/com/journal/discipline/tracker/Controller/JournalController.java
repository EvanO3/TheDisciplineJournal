package com.journal.discipline.tracker.Controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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
import com.journal.discipline.tracker.Model.JournalEntry;
import com.journal.discipline.tracker.Service.JournalService;

import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/api")
public class JournalController {

    @Autowired
    private JournalService journalService;

    @Autowired
    private JwtUtils jwtUtils;

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user/hello")
    public String getUserHello() {
        return "Hello There User";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/hello")
    public String getAdminHello() {
        return "Hello There Admin";
    }
    
    

    @PostMapping("/public/journal")
    public ResponseEntity<JournalDTO> createJournalEntry(@RequestBody JournalDTO journalDTO, HttpServletRequest request) {
       UUID userId =  provideUserIdFromRequest(request);
        JournalDTO savedEntry = journalService.createJournalEntry(journalDTO, userId);
        return new ResponseEntity<>(savedEntry, HttpStatus.CREATED);
  
    }
    

    @GetMapping("/public/journal")
    public ResponseEntity<JournalResponse> getAllEntries(@RequestParam(defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
    @RequestParam(defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
    @RequestParam(defaultValue = AppConstants.SORT_JOURNAL_BY) String sortBy,
    @RequestParam(defaultValue = AppConstants.SORT_DIR) String sortOrder){
        JournalResponse response = journalService.getAllJournalEntry(pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PutMapping("/public/journal")
    public ResponseEntity<JournalDTO> updateJournalEntry(@RequestBody JournalDTO journalEntry, HttpServletRequest request){
        UUID userId = provideUserIdFromRequest(request);
        JournalDTO response = journalService.updateJournalEntry(journalEntry, userId);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/public/journal")
     public ResponseEntity<JournalDTO> deleteEntry(HttpServletRequest request){

        UUID userId = provideUserIdFromRequest(request);
        JournalDTO response = journalService.deleteJournalEntry(userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }




     /*Helper function for user Id */

    private UUID provideUserIdFromRequest(HttpServletRequest request){
        String token = jwtUtils.getJwtTokenFromHeader(request);
        String extractedUserId = jwtUtils.getUserIdFromJwt(token);

        if(extractedUserId.isEmpty() || extractedUserId ==null){
            throw new ApiException("Failed to retrieve user Id");
        }
        return UUID.fromString(extractedUserId);
    }
}
