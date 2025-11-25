package com.journal.discipline.tracker.Service;


import java.util.UUID;

import com.journal.discipline.tracker.DTOs.StreakData;
import com.journal.discipline.tracker.DTOs.SummaryDTO;
import com.journal.discipline.tracker.DTOs.UserDTO;
import com.journal.discipline.tracker.DTOs.UserResponse;

public interface UserService {
    
    UserDTO createUser(UserDTO user);
    UserResponse retrieveUser(UUID userId);
    UserDTO deleteUser(UUID userId);
    UserDTO updateUsername(UserDTO user, UUID userId);
   SummaryDTO getUserDailySummary(UUID userId);
   StreakData updateUserStreakData(UUID userId);


}
