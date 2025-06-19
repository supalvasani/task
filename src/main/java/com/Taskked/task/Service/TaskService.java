package com.Taskked.task.Service;

import com.Taskked.task.DTO.TaskRequestDTO;
import com.Taskked.task.DTO.TaskResponseDTO;
import com.Taskked.task.Exception.InvalidTaskException;
import com.Taskked.task.Exception.TaskNotFoundException;
import com.Taskked.task.Model.Tasks;
import com.Taskked.task.Repo.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepo taskRepo;
    public List<Tasks> getAllTask(String userid) {
        List<Tasks> taskList = taskRepo.alltaskd(userid);
        if (taskList.isEmpty()) {
            throw new TaskNotFoundException("No tasks found for user: " + userid);
        }
        return taskList;
    }

    public Object createTask(TaskRequestDTO taskRequestDTO) {
        Tasks task = new Tasks();
        task.setTitle(taskRequestDTO.getTitle());
        task.setCompleted(taskRequestDTO.isCompleted());
        task.setUserId(taskRequestDTO.getUserId());
        int saved = taskRepo.addTasks(task);
        if (saved <= 0) {
            throw new InvalidTaskException("task not created");
        }
        return task;
    }

    public Tasks updateTask(TaskResponseDTO taskResponseDTO) {
        Tasks task = new Tasks();
        if (taskResponseDTO.getTitle() == null || taskResponseDTO.getTitle().trim().isEmpty()) {
            throw new InvalidTaskException("Title can't be empty");
        }
        if (taskResponseDTO.getTaskid() == 0) {
            throw new InvalidTaskException("Task ID is missing");
        }
        task.setTitle(taskResponseDTO.getTitle());
        task.setCompleted(taskResponseDTO.isCompleted());
        int updated = taskRepo.updateTask(task);
        if (updated <= 0) {
            throw new InvalidTaskException("Task update failed");
        }
        return task;
    }

    public HttpStatusCode deleteTask(TaskResponseDTO taskResponseDTO, HttpStatus httpStatus) {
    }

    public HttpStatusCode completeTask(TaskResponseDTO taskResponseDTO, HttpStatus httpStatus) {
    }


    public Object completeTask(String userid) {
    }

    public Object incompleteTask(String userid) {
    }
}
