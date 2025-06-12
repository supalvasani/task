package com.Taskked.task.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor

public class UserResponseDTO {
    private Long userid;
    private String username;
    private String email;
}
