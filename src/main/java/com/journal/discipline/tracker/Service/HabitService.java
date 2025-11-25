package com.journal.discipline.tracker.Service;

import java.util.UUID;



import com.journal.discipline.tracker.DTOs.HabitDTO;
import com.journal.discipline.tracker.DTOs.HabitResponse;


public interface HabitService {
    
    /*User can create a habit */
    /*Status: Done */
     HabitDTO createHabit(HabitDTO habitDTO, UUID Id);

    /*User can delete their habit 
     * Status:Complete
    */
    void deleteHabit(UUID habitId, UUID Id);


    /*User can edit their habit
     * Status Complete
     */
    HabitDTO updateHabit(HabitDTO HabitDTO, UUID habitId, UUID Id);



    /*User can view all created habits
     * Status:Complete
     */
    HabitResponse getAllHabits(UUID Id);
}
