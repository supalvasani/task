package com.Taskked.task.Controller;

import com.Taskked.task.DTO.TaskResponseDTO;
import com.Taskked.task.Service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class TaskController {
    @Autowired
    private TaskService taskService;

    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody TaskResponseDTO taskResponseDTO){
        return new ResponseEntity<>(taskResponseDTO, HttpStatus.ACCEPTED);
    }
}
