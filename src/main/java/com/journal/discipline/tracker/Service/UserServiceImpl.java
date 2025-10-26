package com.journal.discipline.tracker.Service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.journal.discipline.tracker.DTOs.UserDTO;
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

       /*TODO:
     * Editing user information i.e username
     */


       /*TODO:
     * Deleting user
     */
}
