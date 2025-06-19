package com.Taskked.task.Controller;

import com.Taskked.task.DTO.TaskRequestDTO;
import com.Taskked.task.DTO.TaskResponseDTO;
import com.Taskked.task.Model.Tasks;
import com.Taskked.task.Service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TaskController {
    @Autowired
    private TaskService taskService;

    @PostMapping("/task")
    public ResponseEntity<?> createTask(@RequestBody TaskRequestDTO taskResponseDTO){
        return new ResponseEntity<>(taskService.createTask(taskResponseDTO), HttpStatus.ACCEPTED);
    }

    @GetMapping("/users/{id}/tasks")
    public ResponseEntity<?> getAllTask(@PathVariable("id") String userid){
        List<Tasks> task = taskService.getAllTask(userid);
        return new ResponseEntity<>(task,HttpStatus.ACCEPTED);
    }

    @PutMapping("/tasks/{id}")
    public ResponseEntity<?> UpdateTasks(@RequestBody TaskResponseDTO taskResponseDTO){
        Tasks task = taskService.updateTask(taskResponseDTO);
        return new ResponseEntity<>(task,HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<?> deleteTask(@RequestBody TaskResponseDTO taskResponseDTO){
        return new ResponseEntity<>(taskService.deleteTask(taskResponseDTO,HttpStatus.ACCEPTED));
    }

    @PutMapping("/tasks/{id}/complete")
    public ResponseEntity<?> completedTask(@RequestBody TaskResponseDTO taskResponseDTO){
        return new ResponseEntity<>(taskService.completeTask(taskResponseDTO,HttpStatus.ACCEPTED));
    }

    @GetMapping("/users/{id}/tasks/completed")
    public ResponseEntity<?> completedTasks(@PathVariable("id") String userid){
        return new ResponseEntity<>(taskService.completeTask(userid),HttpStatus.ACCEPTED);
    }

    @GetMapping("/users/{id}/tasks/incomplete")
    public ResponseEntity<?> incompleteTasks(@PathVariable("id") String userid){
        return new ResponseEntity<>(taskService.incompleteTask(userid),HttpStatus.ACCEPTED);
    }
}
