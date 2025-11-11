package com.journal.discipline.tracker.Service;


import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.journal.discipline.tracker.DTOs.JournalDTO;
import com.journal.discipline.tracker.DTOs.JournalResponse;
import com.journal.discipline.tracker.DTOs.UserDTO;
import com.journal.discipline.tracker.DTOs.UserResponse;
import com.journal.discipline.tracker.Exceptions.ApiException;
import com.journal.discipline.tracker.Model.JournalEntry;
import com.journal.discipline.tracker.Model.User;
import com.journal.discipline.tracker.Repository.JournalRepository;
import com.journal.discipline.tracker.Repository.UserRespository;

@Service
public class UserServiceImpl implements UserService {
 

  @Autowired
private ModelMapper modelMapper;
@Autowired
private UserRespository userRespository;


@Autowired
private JournalRepository journalRepository;

@Autowired
private BCryptPasswordEncoder passwordEncoder;

private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Override
    public UserDTO createUser(UserDTO user) {
        logger.info("This is the user being pass: {}", user);

        User newUser = modelMapper.map(user, User.class);

        Optional<User> savedUser = userRespository.findByUsername(newUser.getUsername());
        if(savedUser.isPresent()){
            throw new ApiException("User already Exists");
        }

        
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        userRespository.save(newUser);
        return modelMapper.map(newUser, UserDTO.class);
        
    }
    /*TODO:
     *Return the users Journal Titles while retriving them,
     Maybe refactor later to add information about journals
     */
    @Override
    public UserResponse retrieveUser(UUID userId) {
        User user = userRespository.findById(userId)
        .orElseThrow(()-> new ApiException("User with Id : " + userId + " Does not exist"));


        List<JournalEntry> usersJournalEntries = journalRepository.findByUserId(userId);
        // if(user == null && usersJournalEntries.isEmpty()){
        //     throw new  ApiException("User does not exist" );
        // }

       List<String> journals = usersJournalEntries.stream().map(entries -> entries.getTitle()).collect(Collectors.toList());
        //set the user response information and return the user response
        UserResponse userResponse = new UserResponse();
        userResponse.setUsername(user.getUsername());
        userResponse.setStreakCount(user.getStreakCount());
        userResponse.setAvgDisciplineScore(user.getAvgDisciplineScore());
        userResponse.setLongestStreak(user.getLongestStreak());
        userResponse.setJournalTitle(journals);
        return userResponse;
    }
    
    
    
    
        
    @Override
    public UserDTO deleteUser(UUID userId) {
       User user =userRespository.findById(userId)
       .orElseThrow(() -> new ApiException("Cannot find user"));
       
       userRespository.deleteById(userId);
       
       return modelMapper.map(user, UserDTO.class);
        
    }
    @Override
    public UserDTO updateUsername(UserDTO user, UUID userId) {
        /*Map the DTO from the user*/
        User newUser = modelMapper.map(user, User.class) ;
        /*Then retrieve the current user */
        User savedUser = userRespository.findById(userId).orElseThrow(() -> new ApiException("Unable to retriev user from the database"));
        /*set the information using setterrs */
        savedUser.setUsername(newUser.getUsername());

        /*resave the user */
        userRespository.save(savedUser);
        return modelMapper.map(savedUser, UserDTO.class);
    }
    




     
}
