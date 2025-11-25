package com.journal.discipline.tracker.Service;

import java.time.LocalDate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;


import com.journal.discipline.tracker.DTOs.HabitDTO;
import com.journal.discipline.tracker.DTOs.HabitLogDTO;
import com.journal.discipline.tracker.DTOs.HabitLogResponse;
import com.journal.discipline.tracker.Exceptions.ApiException;
import com.journal.discipline.tracker.Exceptions.UnauthorizationException;
import com.journal.discipline.tracker.Model.Habit;
import com.journal.discipline.tracker.Model.HabitLog;
import com.journal.discipline.tracker.Model.User;
import com.journal.discipline.tracker.Repository.HabitLogRepository;
import com.journal.discipline.tracker.Repository.HabitRepository;
import com.journal.discipline.tracker.Repository.UserRepository;

@Service
public class HabitLogServiceImpl implements HabitLogService {

    private static final Logger logger = LoggerFactory.getLogger(HabitLogServiceImpl.class);
    @Autowired
    private HabitLogRepository habitLogRepository;

    @Autowired 
    private ModelMapper modelMapper;
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HabitRepository habitRepository;

    /* User can only create 1 habit log for 1 habit per day so no duplicate of the
     * same habit
     * 
     */
    @Override
    @Transactional
    public HabitLogDTO createLog(UUID userId, UUID habitId, LocalDate logDate) {
   /*Find the habit and the user */

   User user = userRepository.getReferenceById(userId);
   Habit habit = habitRepository.findById(habitId).orElseThrow(() ->
         new ApiException("Failed to Retrieve Habit"));

       Optional<HabitLog> habitLog = habitLogRepository.
       findByHabitIdAndUserIdAndLogDate(userId, habitId, logDate);
       /*refactor to check id for of the present habit, if its equal deny ability
        * to make another log
      */

       if(!habitLog.isEmpty()){
        throw new ApiException("Use must wait until Tomorrow to make another log");
       }

       

        HabitLog usersHabit = new HabitLog();
       usersHabit.setUser(user);
       usersHabit.setHabit(habit);
       usersHabit.setLogDate(LocalDate.now());
        habitLogRepository.save(usersHabit);

        return modelMapper.map(usersHabit, HabitLogDTO.class);

      
    }





    @Override
    public HabitLogResponse updateCompletionStatus(UUID userId, UUID habitLogId) {
     HabitLog usersHabit = habitLogRepository.findById(habitLogId)
       .orElseThrow(() -> new ApiException("Failed to retrieve habit log"));


       if(!usersHabit.getUser().getId().equals(userId)){
        throw new UnauthorizationException("User Cannot Edit this Resource");
       }
       usersHabit.setCompletionStatus(true);
       habitLogRepository.save(usersHabit);

       HabitLogResponse habitLog = modelMapper.map(usersHabit, HabitLogResponse.class);
       
       return habitLog;
    }

   
   
 
    @Override
    public List<HabitLogResponse> getDailyHabitLog(UUID userId) {
       /*Find the habit log based on log date and userId */
       List<HabitLog> habitLog = habitLogRepository.findAllByUserIdAndLogDate(userId, LocalDate.now());
       
       
       
       if(habitLog.isEmpty()){
           throw new ApiException("Failed to retrieve daily Log");
        }
        
       return  habitLog.stream().map(log->{
         HabitLogResponse response = new HabitLogResponse();
         response.setHabit(modelMapper.map(log.getHabit(), HabitDTO.class));
         response.setCompletionStatus(log.isCompletionStatus());
         response.setLogDate(log.getLogDate());
         return response;
        }).toList();


    }
    
}
