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
    public List<TaskResponseDTO> getAllTask(String userid) {
        List<Tasks> taskList = taskRepo.alltaskd(userid);
        if (taskList.isEmpty()) {
            throw new TaskNotFoundException("No tasks found for user: " + userid);
        }
        return taskList.stream()
                .map(task -> new TaskResponseDTO(
                        task.getTaskid(),
                        task.getTitle(),
                        task.isCompleted()
                ))
                .toList();

    }

    public Object createTask(TaskRequestDTO taskRequestDTO) {
        Tasks task = new Tasks();
        task.setTitle(taskRequestDTO.getTitle());
//        task.setCompleted(false);
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

        try {
            Tasks task = taskRepo.getTaskById(taskResponseDTO.getTaskid());
            task.setTitle(taskResponseDTO.getTitle());
            task.setCompleted(taskResponseDTO.isCompleted());
            taskRepo.updateTask(task);
            return task;
        } catch (Exception e) {
            throw new RuntimeException("task not updated");
        }
    }

    public Tasks deleteTask(long taskId) {
        if (taskId == 0) {
            throw new InvalidTaskException("Task ID is missing");
        }
        Tasks task = taskRepo.getTaskById(taskId);
        int deleted = taskRepo.deleteTask(taskId);
        if (deleted <= 0) {
            throw new TaskNotFoundException("task is not found");
        }
        return new Tasks();
    }


    public Tasks completeTask(long taskId) {
        Tasks task = taskRepo.getTaskById(taskId);
        if (task == null) {
            throw new RuntimeException("Task not found with ID: " + taskId);
        }

        if (!task.isCompleted()) {
            taskRepo.completeTask(taskId);
            task.setCompleted(true);
        }

        return task;
    }


    public List<TaskResponseDTO> completeTask(String userid) {
        List<Tasks> tasks = taskRepo.completedtask(userid);
        if(tasks.isEmpty()){
            throw new TaskNotFoundException("No complete tasks for user: " + userid);
        }
        return tasks.stream()
                .map(task -> new TaskResponseDTO(
                        task.getTaskid(),
                        task.getTitle(),
                        task.isCompleted()
                ))
                .toList();
    }

    public List<TaskResponseDTO> incompleteTask(String userid) {
        List<Tasks> tasks = taskRepo.incompletetask(userid);
        if(tasks.isEmpty()){
            throw new TaskNotFoundException("No incomplete tasks for user: " + userid);
        }
        return tasks.stream()
                .map(task -> new TaskResponseDTO(
                        task.getTaskid(),
                        task.getTitle(),
                        task.isCompleted()
                ))
                .toList();
    }
}
