package com.Taskked.task.Exception;

import org.springframework.web.bind.annotation.ResponseStatus;

public class DatabaseException extends RuntimeException {
    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
