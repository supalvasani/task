package com.Taskked.task.Repo;

import com.Taskked.task.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class userRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private PasswordEncoder passwordEncoder;

    public List<User> findall(){
        String sql = "select * from users";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(User.class));
        /*Takes each row from your SQL result
            Matches column names with Java bean properties (fields like id, name, email)
            Creates and returns a User object with those values */
    }

    public User findById(Long id){
        String sql = "select * from users WHERE id = ?";
        return jdbcTemplate.queryForObject(sql,new Object[]{id},new BeanPropertyRowMapper<>(User.class));
    }

    public int saveRegister(User user){
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        String sql = "INSERT INTO users (username,email,password) VALUES (?,?,?)";
        return jdbcTemplate.update(sql,user.getUsername(),user.getEmail(),hashedPassword);
    }

    public User findByEmail(String email){
        String sql = "SELECT * FROM users WHERE email = ?";
        return jdbcTemplate.queryForObject(sql,new Object[]{email}, new BeanPropertyRowMapper<>(User.class));
    }

    public boolean login(String email,String rawpassword){
        try{
            User user = findByEmail(email);
            return passwordEncoder.matches(rawpassword, user.getPassword());
        }catch(EmptyResultDataAccessException e){
            return false;
        }
    }

    public boolean  changePassword(Long userId,String oldPassword, String newPassword){
        User user = findById(userId);
        if(passwordEncoder.matches(oldPassword,user.getPassword())){
            String hashedNewPassword = passwordEncoder.encode(newPassword);
            String sql = "UPDATE users SET password = ? WHERE id = ?";
            jdbcTemplate.update(sql,hashedNewPassword,userId);
            return true;
        }else{
            return false;
        }
    }

    public int update(User user){
        String sql = "UPDATE users SET username = ?, email = ? WHERE id = ?";
        return jdbcTemplate.update(sql, user.getUsername(), user.getEmail(), user.getUserid());
    }

    public int deleteById(Long id) {
        String sql = "DELETE FROM users WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

}
