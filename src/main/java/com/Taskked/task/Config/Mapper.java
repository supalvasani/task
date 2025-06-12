package com.Taskked.task.Config;

import com.Taskked.task.DTO.TaskResponseDTO;
import com.Taskked.task.DTO.UserResponseDTO;
import com.Taskked.task.Model.Tasks;
import com.Taskked.task.Model.User;

public class Mapper {
    public static UserResponseDTO toUserDTO(User user){
        return new UserResponseDTO(user.getUserid(),user.getUsername(),user.getEmail());
    }

    public static TaskResponseDTO toTaskDTO(Tasks tasks){
        return new TaskResponseDTO(tasks.getTaskid(),tasks.getTitle(),tasks.isCompleted(),tasks.getDueDate());
    }
}
