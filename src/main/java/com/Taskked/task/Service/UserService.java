package com.Taskked.task.Service;

import com.Taskked.task.DTO.LoginRequestDTO;
import com.Taskked.task.DTO.UserRequestDTO;
import com.Taskked.task.Model.User;
import com.Taskked.task.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service

public class UserService {
    @Autowired
    private UserRepo userrepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User register(UserRequestDTO userRequestDTO) {
        if (userrepo.findByEmail(userRequestDTO.getEmail()) != null) {
        throw new RuntimeException("user's email already exist");
        }
        User user = new User();
        user.setEmail(userRequestDTO.getEmail());
        user.setPassword(userRequestDTO.getPassword());
        user.setUsername(userRequestDTO.getUsername());
        user.setUserid(UUID.randomUUID().toString());
        User registeredUser = userrepo.saveRegister(user);
        if (registeredUser == null || registeredUser.getUserid() == null || registeredUser.getUserid().isEmpty()) {
            throw new RuntimeException("User registration failed: Could not assign or save UUID.");
        }
        return registeredUser;
    }

    public User login(LoginRequestDTO loginRequestDTO) {
       User user = userrepo.findByEmail(loginRequestDTO.getEmail());
       if(user != null && passwordEncoder.matches(loginRequestDTO.getPassword(), user.getPassword())){
            return user;
        }else{
           throw new RuntimeException("incorrect password/user not found");
       }
    }

    public User updateUserDetails(UserRequestDTO userRequestDTO, String userid) {
        User existingUser = userrepo.findById(userid); // Fetch the existing user
        if (existingUser == null) {
            throw new RuntimeException("User not found with ID: " + userid);
        }

        if (userRequestDTO.getUsername() != null && !userRequestDTO.getUsername().isEmpty()) {
            existingUser.setUsername(userRequestDTO.getUsername());
        }
        if (userRequestDTO.getEmail() != null && !userRequestDTO.getEmail().isEmpty()) {
            existingUser.setEmail(userRequestDTO.getEmail());
        }

        int changeuser = userrepo.updateProfile(existingUser);
        if (changeuser > 0) {
            return existingUser;
        } else {
            throw new RuntimeException("User profile update not successful for user: " + userid);
        }
    }

    public User getUserByID(String userid) {
        User user = userrepo.findById(userid);
        return user;
    }
}
