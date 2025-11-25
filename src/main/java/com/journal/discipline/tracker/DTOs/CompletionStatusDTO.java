package com.journal.discipline.tracker.DTOs;


import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
public class CompletionStatusDTO {
    
    private Long completed_logs;
    private Long total_logs;


    
    public CompletionStatusDTO  (Long totalLogs, Long completedLogs) {
        this.total_logs = totalLogs != null ? totalLogs : 0L;
        this.completed_logs = completedLogs != null ? completedLogs : 0L;
    }
    
    public boolean allComplete() {
        return total_logs > 0 && total_logs.equals(completed_logs);
    }
}
