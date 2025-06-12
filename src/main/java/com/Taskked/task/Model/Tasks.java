package com.Taskked.task.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tasks {
    private Long taskid;
    private String title;
    private boolean completed;
    private LocalDateTime dueDate;
    private Long userId;
}

