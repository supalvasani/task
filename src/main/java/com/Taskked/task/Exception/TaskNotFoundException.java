package com.Taskked.task.Exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//Unchecked Exception (extends RuntimeException)
//Most common in Spring Boot (e.g., for invalid requests, not found, etc.)

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(String message) {
            super(message);
    }
}

