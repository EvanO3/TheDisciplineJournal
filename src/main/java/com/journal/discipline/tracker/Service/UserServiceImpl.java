package com.journal.discipline.tracker.Service;

import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.journal.discipline.tracker.DTOs.UserDTO;
import com.journal.discipline.tracker.DTOs.UserResponse;
import com.journal.discipline.tracker.Exceptions.ApiException;
import com.journal.discipline.tracker.Model.User;
import com.journal.discipline.tracker.Repository.UserRespository;

@Service
public class UserServiceImpl implements UserService {
 /*TODO:
     * creating a user
*/

  @Autowired
private ModelMapper modelMapper;
@Autowired
private UserRespository userRespository;

private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Override
    public UserDTO createUser(UserDTO user) {
        logger.info("This is the user being pass: {}", user);

        User newUser = modelMapper.map(user, User.class);

        Optional<User> savedUser = userRespository.findByUsername(newUser.getUsername());
        if(savedUser.isPresent()){
            throw new ApiException("User already Exists");
        }

        userRespository.save(newUser);
        return modelMapper.map(newUser, UserDTO.class);
        
    }
    /*TODO:
     * Get User information  from db by id
     */
    @Override
    public UserResponse retrieveUser(UUID userId) {
        User user = userRespository.findById(userId)
        .orElseThrow(()-> new ApiException("User with Id : " + userId + " Does not exist"));

        //set the user response information and return the user response
        UserResponse userResponse = new UserResponse();
        userResponse.setUsername(user.getUsername());
        userResponse.setStreakCount(user.getStreakCount());
        userResponse.setAvgDisciplineScore(user.getAvgDisciplineScore());
        userResponse.setLongestStreak(user.getLongestStreak());
        return userResponse;
    }
    
    
    
    
           /*TODO:
         * Deleting user
         */
    
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
    



       /*TODO:
     * Editing user information i.e username
     */
    

     
}
