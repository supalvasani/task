package com.Taskked.task.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginRequestDTO {
    private String email;
    private String password;
}
