package com.Taskked.task.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskRequestDTO {
    private String title;
    private boolean completed;
    private String userId;
}
