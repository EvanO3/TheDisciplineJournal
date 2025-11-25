package com.journal.discipline.tracker.Controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.journal.discipline.tracker.DTOs.HabitDTO;
import com.journal.discipline.tracker.DTOs.HabitResponse;

import com.journal.discipline.tracker.Jwt.JwtUtils;
import com.journal.discipline.tracker.Service.HabitService;

import jakarta.servlet.http.HttpServletRequest;

@RequestMapping("/api")
@RestController
public class HabitController {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private HabitService habitService;

    @PostMapping("/public/habit")
    public ResponseEntity<HabitDTO> createHabit(@RequestBody HabitDTO habitDTO, HttpServletRequest request){
        
            UUID userId = jwtUtils.provideUserIdFromRequest(request);
            HabitDTO habit = habitService.createHabit(habitDTO, userId);
            return new ResponseEntity<>(habit, HttpStatus.CREATED);
        
    }


      @GetMapping("/public/habit")
    public ResponseEntity<HabitResponse> getAllHabits(HttpServletRequest request){
            UUID userId = jwtUtils.provideUserIdFromRequest(request);
           HabitResponse response = habitService.getAllHabits(userId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        
    }

    @DeleteMapping("/public/habit/{habitId}")
    public ResponseEntity<HabitResponse> deleteHabit(HttpServletRequest request, @PathVariable UUID habitId){
            UUID userId = jwtUtils.provideUserIdFromRequest(request);
            habitService.deleteHabit(habitId, userId);
            return new ResponseEntity<>(HttpStatus.OK);
        
    }


    @PutMapping("/public/habit/{habitId}")
    public ResponseEntity<HabitDTO> updateHabit(@PathVariable UUID habitId, @RequestBody HabitDTO habitDTO, HttpServletRequest request){
        UUID userId = jwtUtils.provideUserIdFromRequest(request);
        HabitDTO responseDTO = habitService.updateHabit(habitDTO, habitId, userId);
        return new ResponseEntity<>(responseDTO, HttpStatus.ACCEPTED);

    }


}
