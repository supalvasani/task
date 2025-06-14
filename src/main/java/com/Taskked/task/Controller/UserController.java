package com.Taskked.task.Controller;

import com.Taskked.task.DTO.LoginRequestDTO;
import com.Taskked.task.DTO.UserRequestDTO;
import com.Taskked.task.DTO.UserResponseDTO;
import com.Taskked.task.Model.User;
import com.Taskked.task.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRequestDTO userRequestDTO){
        try{
            User user = userService.register(userRequestDTO);
            return new ResponseEntity<>(user,HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println("email already exist/or server side error");
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequestDTO){
        try{
            User user = userService.login(loginRequestDTO);
            return new ResponseEntity<>(user,HttpStatus.ACCEPTED);
        } catch (Exception e) {
            System.out.println("wrong userid/password");
            return new ResponseEntity<>(e.getMessage(),HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUserDetails(@RequestBody UserRequestDTO userRequestDTO,@PathVariable("id") Long userid){
        try {
            User user = userService.updateUserDetails(userRequestDTO,userid);
            return new ResponseEntity<>(user,HttpStatus.CONTINUE);
        } catch (RuntimeException e) {
            throw new RuntimeException("current password is incorrect");
        }
    }
}