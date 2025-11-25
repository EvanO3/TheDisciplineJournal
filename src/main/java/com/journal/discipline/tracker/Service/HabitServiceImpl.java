package com.journal.discipline.tracker.Service;

import java.util.List;

import java.util.UUID;


import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.journal.discipline.tracker.Model.User;
import com.journal.discipline.tracker.DTOs.HabitDTO;
import com.journal.discipline.tracker.DTOs.HabitResponse;
import com.journal.discipline.tracker.Exceptions.ApiException;
import com.journal.discipline.tracker.Exceptions.ResourceNotFound;
import com.journal.discipline.tracker.Exceptions.UnauthorizationException;
import com.journal.discipline.tracker.Model.Habit;
import com.journal.discipline.tracker.Repository.HabitRepository;
import com.journal.discipline.tracker.Repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class HabitServiceImpl implements HabitService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private HabitRepository habitRepository;

    @Autowired
    private UserRepository userRespository;

    @Autowired
    private ModelMapper modelMapper;

     
    @Transactional
    public HabitDTO createHabit(HabitDTO habitDTO, UUID Id) {
       
        /*Map the Data given by the user to habit model */
        Habit createdHabit = modelMapper.map(habitDTO, Habit.class);
        /* Check in the DB if they have a habit like this*/
        Habit storedHabits = habitRepository.findByHabitName(createdHabit.getHabitName());
        if(storedHabits != null){
            throw new ApiException("Habit with the same title already exists");
        }

       
        User user = userRespository.getReferenceById(Id);
        
        /*Store the user in the db */
        createdHabit.setUser(user);
        habitRepository.save(createdHabit);

        return modelMapper.map(createdHabit, HabitDTO.class);

        


    }

    @Override
    @Transactional
    public void deleteHabit(UUID habitId, UUID Id) {
        
        /*Query Habit using habit Id
        * if the habit isn't found throw an error
        */
        Habit userSavedHabit = habitRepository.findById(habitId).orElseThrow(
            ()-> new ResourceNotFound("Failed to retrieve habit with id: "+  habitId));
            
            /*if the habit is found but doesn't belong to that user throw an error
             * using compareTo function will check both uuid, if returns 0 means they are equal
             */
            
        if(userSavedHabit.getUser().getId().compareTo(Id) != 0){
           /*Reason for throwing 403 both times is security based
            * but in different settings throw 404 as you would not 
            sources to know if the resource is there or not if they dont have access
            */
            throw new UnauthorizationException("User does not have access to this habit");
        }
        habitRepository.deleteById(habitId);
      /*If if it return null
       * throw Exception with 404 response
       * 
       * if it returns a value, then check userId to see if it matchs 
       * the one found in db by using .getUserId
       * if a match then proceed with delete
       * if it doesnt match then throw 403 or 404 depending on security
       */

       


    }

    @Override
    @Transactional
    public HabitDTO updateHabit(HabitDTO HabitDTO, UUID habitId, UUID Id) {
        
        Habit userSavedHabit = habitRepository.findById(habitId).orElseThrow(
            ()-> new ResourceNotFound("Failed to retrieve habit with id: "+  habitId));


                if(userSavedHabit.getUser().getId().compareTo(Id) != 0){
           /*Reason for throwing 403 both times is security based
            * but in different settings throw 404 as you would not 
            sources to know if the resource is there or not if they dont have access
            */
            throw new UnauthorizationException("User does not have access to this habit");
        }
            
            
        Habit userChangedHabit = modelMapper.map(HabitDTO, Habit.class);
        userChangedHabit.setHabitId(habitId);
        userChangedHabit.setUser(userSavedHabit.getUser());
        userSavedHabit = habitRepository.save(userChangedHabit);

        return modelMapper.map(userSavedHabit, HabitDTO.class);
    
          



    }

    @Override
    public HabitResponse getAllHabits(UUID Id) {
        List<Habit> usersSavedHabits = habitRepository.findHabitsByUserId(Id);
        if(usersSavedHabits.size() == 0){
            throw new ApiException("No Habits found for user");
        }
        List<HabitDTO> habitDTOs = usersSavedHabits.stream()
        .map(habit -> modelMapper.map(habit, HabitDTO.class)).toList();
        

        HabitResponse response = new HabitResponse();
        response.setHabitContent(habitDTOs);
        return response;

      
        
    }
    
}
