package com.journal.discipline.tracker.Service;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.stereotype.Service;

import com.journal.discipline.tracker.DTOs.CompletionStatusDTO;

import com.journal.discipline.tracker.DTOs.HabitLogResponse;
import com.journal.discipline.tracker.DTOs.JournalDTO;

import com.journal.discipline.tracker.DTOs.StreakData;
import com.journal.discipline.tracker.DTOs.SummaryDTO;
import com.journal.discipline.tracker.DTOs.UserDTO;
import com.journal.discipline.tracker.DTOs.UserResponse;
import com.journal.discipline.tracker.Exceptions.ApiException;
import com.journal.discipline.tracker.Exceptions.ResourceNotFound;
import com.journal.discipline.tracker.Model.HabitLog;
import com.journal.discipline.tracker.Model.JournalEntry;
import com.journal.discipline.tracker.Model.User;
import com.journal.discipline.tracker.Repository.HabitLogRepository;
import com.journal.discipline.tracker.Repository.JournalRepository;
import com.journal.discipline.tracker.Repository.UserRepository;
import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {
 

  @Autowired
private ModelMapper modelMapper;
@Autowired
private UserRepository userRespository;


@Autowired
private JournalRepository journalRepository;

@Autowired
private HabitLogRepository habitLogRepository;

@Autowired
private BCryptPasswordEncoder passwordEncoder;

private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Override

    @Transactional
    public UserDTO createUser(UserDTO user) {
      
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
    

    /*
     * 
     *  Daily summary should return all the information to the user
         The Habit log for the day -done
         Their Score i.e discipline score, streak
         Journal Entry for the day

     */
    @Override

    public SummaryDTO getUserDailySummary(UUID userId) {
        /*Find Users score */
        StreakData userSteakData= userRespository.findStreakDataById(userId);
        SummaryDTO userSummary = new SummaryDTO();

        /*Find the Users Habit log for the day */

        List<HabitLog> usersDailyLog =habitLogRepository.findAllByUserIdAndLogDate(userId, LocalDate.now());
        if(usersDailyLog.isEmpty()){
            userSummary.setHabitLog(null);
        }


        Optional<JournalEntry> journal = journalRepository.findByUserIdAndSubmissionDate(userId, LocalDate.now());

       

        userSummary.setStreakData(userSteakData);
        userSummary.setJournalEntry(journal.map(j ->  modelMapper.map(j, JournalDTO.class)).orElse(null));
        userSummary.setHabitLog(usersDailyLog.stream().map(h -> modelMapper.map(h, HabitLogResponse.class)).collect(Collectors.toList()));

       return userSummary;

    }
    

    /*There is a TODO:
     * 
     */
    @Transactional
    public StreakData updateUserStreakData(UUID userId) {
     
        User user = userRespository.findById(userId).orElseThrow(()-> new ApiException("Failed to find user"));

        CompletionStatusDTO completionStatus = habitLogRepository.findCompletionStatus(userId, LocalDate.now());

        if(completionStatus.allComplete()){
            LocalDate today = LocalDate.now();

            if(user.getStreakIncrementDate() == null || !user.getStreakIncrementDate().equals(today)){
                user.incrementStreak();
                user.setStreakIncrementDate(today);
                userRespository.save(user);

            }

        }else if(!completionStatus.allComplete() ){
            /*TODO:
             * Find a way to remove streak if user doesn't complete
             * future refinement
             */
        }

    

        StreakData usersStreakData = new StreakData();
        usersStreakData.setStreakCount(user.getStreakCount());
        usersStreakData.setLongestStreak(user.getLongestStreak());

        return usersStreakData;

    }
    




     
}
