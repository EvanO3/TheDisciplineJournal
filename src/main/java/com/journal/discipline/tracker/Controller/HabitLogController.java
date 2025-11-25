package com.journal.discipline.tracker.Controller;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.journal.discipline.tracker.DTOs.HabitLogDTO;
import com.journal.discipline.tracker.DTOs.HabitLogResponse;
import com.journal.discipline.tracker.Jwt.JwtUtils;
import com.journal.discipline.tracker.Service.HabitLogService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class HabitLogController {

@Autowired
private JwtUtils jwtUtils;
@Autowired
private HabitLogService habitLogService;


    @PostMapping("/public/habitlog/{habitId}/{logDate}")
    public ResponseEntity<HabitLogDTO> createHabitLog(@PathVariable UUID habitId, @PathVariable LocalDate logDate, HttpServletRequest request){
        UUID userId = jwtUtils.provideUserIdFromRequest(request);
        HabitLogDTO habit = habitLogService.createLog(userId, habitId, logDate);

        return new ResponseEntity<>(habit, HttpStatus.CREATED);
        
    }

 

    @PutMapping("/public/habitlog/{habitLogId}")
    private ResponseEntity<HabitLogResponse> markHabitComplete(@PathVariable UUID habitLogId, HttpServletRequest request){
        UUID userId = jwtUtils.provideUserIdFromRequest(request);
        
        HabitLogResponse changeStatus = habitLogService.updateCompletionStatus(userId, habitLogId);
        return new ResponseEntity<>(changeStatus, HttpStatus.OK);
    }


    @GetMapping("/public/habitlog")
    private ResponseEntity<List<HabitLogResponse>> getDailyHabitLog( HttpServletRequest request){
        UUID userId = jwtUtils.provideUserIdFromRequest(request);
        
        List<HabitLogResponse> response = habitLogService.getDailyHabitLog(userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    
    
}
