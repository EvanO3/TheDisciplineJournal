package com.journal.discipline.tracker.Service;

import java.util.UUID;

import com.journal.discipline.tracker.DTOs.UserDTO;
import com.journal.discipline.tracker.DTOs.UserResponse;

public interface UserService {
    
    UserDTO createUser(UserDTO user);
    UserResponse retrieveUser(UUID userId);
}
