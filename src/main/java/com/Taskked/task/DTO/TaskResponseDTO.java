package com.Taskked.task.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TaskResponseDTO {
    private Long taskid;
    private String title;
    private boolean completed;
}
