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
public class UserRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> findall(){
        String sql = "SELECT * from users";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(User.class));
        /*Takes each row from your SQL result
            Matches column names with Java bean properties (fields like id, name, email)
            Creates and returns a User object with those values */
    }

    public User findById(String id){
        String sql = "SELECT * from users WHERE userid = ?";
        return jdbcTemplate.queryForObject(sql,new Object[]{id},new BeanPropertyRowMapper<>(User.class));
    }

    public User saveRegister(User user){
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        String sql = "INSERT INTO users (userid,username,email,password) VALUES (?,?,?,?)";
        int rowsAffected =  jdbcTemplate.update(sql,user.getUserid(),user.getUsername(),user.getEmail(),hashedPassword);
        if(rowsAffected>0){
            return user;
        }else{
            throw new RuntimeException("User registration failed: no rows affected");
        }
    }

    public User findByEmail(String email){
        String sql = "SELECT * FROM users WHERE email = ?";
        try {
            return jdbcTemplate.queryForObject(sql,new Object[]{email}, new BeanPropertyRowMapper<>(User.class));
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    public int updateProfile(User user){
        String sql = "UPDATE users SET username = ?, email = ? WHERE userid = ?";
        return jdbcTemplate.update(sql, user.getUsername(), user.getEmail(),user.getUserid());
    }

    public int updatePassword(String userid, String newHashedPassword){
        String sql = "UPDATE users SET password = ? WHERE userid = ?";
        return jdbcTemplate.update(sql, newHashedPassword, userid);
    }
    public int deleteById(String userid) {
        String sql = "DELETE FROM users WHERE userid = ?";
        return jdbcTemplate.update(sql, userid);
    }

}
