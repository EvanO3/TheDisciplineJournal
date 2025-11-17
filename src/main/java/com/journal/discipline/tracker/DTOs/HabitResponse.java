package com.journal.discipline.tracker.DTOs;

import java.util.List;

import com.journal.discipline.tracker.Enums.HabitType;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HabitResponse {
    List<HabitDTO> habitContent;

}
