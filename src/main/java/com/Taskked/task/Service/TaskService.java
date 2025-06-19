package com.Taskked.task.Service;

import com.Taskked.task.DTO.TaskRequestDTO;
import com.Taskked.task.DTO.TaskResponseDTO;
import com.Taskked.task.Exception.InvalidTaskException;
import com.Taskked.task.Exception.TaskNotFoundException;
import com.Taskked.task.Model.Tasks;
import com.Taskked.task.Repo.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
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
        if (taskResponseDTO.getTitle() == null || taskResponseDTO.getTitle().trim().isEmpty()) {
            throw new InvalidTaskException("Title can't be empty");
        }
        if (taskResponseDTO.getTaskid() == 0) {
            throw new InvalidTaskException("Task ID is missing");
        }

        Tasks task = new Tasks();
        task.setTitle(taskResponseDTO.getTitle());
        task.setCompleted(taskResponseDTO.isCompleted());
        task.setTaskid(taskResponseDTO.getTaskid());

        int updated = taskRepo.updateTask(task);
        if (updated <= 0) {
            throw new InvalidTaskException("Task update failed");
        }
        return taskRepo.getTaskById(task.getTaskid());
    }




    public Tasks deleteTask(long taskId) {
        if (taskId == 0) {
            throw new InvalidTaskException("Task ID is missing");
        }
        int deleted = taskRepo.deleteTask(taskId);
        if (deleted <= 0) {
            throw new TaskNotFoundException("task is not found");
        }
        return new Tasks();
    }


    public Tasks completeTask(long taskId) {
        if (taskId == 0) {
            throw new InvalidTaskException("Task ID is missing");
        }
        int updated = taskRepo.completeTask(taskId);
        if (updated <= 0) {
            throw new TaskNotFoundException("Task not found or not updated");
        }
        Tasks updatedTask = taskRepo.getTaskById(taskId);
        if (updatedTask == null) {
            throw new TaskNotFoundException("Task not found after update");
        }
        return updatedTask;
    }


    public List<Tasks> completeTask(String userid) {
        List<Tasks> tasks = taskRepo.completedtask(userid);
        if(tasks.isEmpty()){
            throw new TaskNotFoundException("No complete tasks for user: " + userid);
        }
        return tasks;
    }

    public Object incompleteTask(String userid) {
        List<Tasks> tasks = taskRepo.incompletetask(userid);
        if(tasks.isEmpty()){
            throw new TaskNotFoundException("No incomplete tasks for user: " + userid);
        }
        return tasks;
    }
}
