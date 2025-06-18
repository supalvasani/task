package com.Taskked.task.Repo;

import com.Taskked.task.Model.Tasks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TaskRepo {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Tasks> alltaskd(String userId){
        String sql = "SELECT taskid, title, completed FROM tasks WHERE user_id = ?"; // Added 'completed' to SELECT
        return jdbcTemplate.query(sql, new Object[]{userId}, new BeanPropertyRowMapper<>(Tasks.class));
    }

    public int addTasks(Tasks tasks){
        String sql = "INSERT INTO tasks (title, completed, user_id) VALUES (?,?,?)";
        int rowsUpdated = jdbcTemplate.update(sql,tasks.getTitle(),tasks.isCompleted(),tasks.getUserId());
        if(rowsUpdated>0){
            return 1;
        }else{
            throw new RuntimeException("tasks not added");
        }
    }

    public int updateTask(Tasks tasks){
        String sql = "UPDATE tasks SET title = ? WHERE taskid = ?";
        int rowsUpdated = jdbcTemplate.update(sql,tasks.getTitle(), tasks.getTaskid());
        if(rowsUpdated>0){
            return 1;
        }else{
            throw new RuntimeException("title not updated");
        }
    }

    public int deleteTask(long taskid){
        String sql = "DELETE from tasks WHERE taskid = ?";
        int rowsUpdated = jdbcTemplate.update(sql, taskid);
        if(rowsUpdated>0){
            return 1;
        }else{
            throw new RuntimeException("task not deleted");
        }
    }

    public int completeTask(long taskId) {
        String sql = "UPDATE tasks SET completed = TRUE WHERE taskid = ?";
        int rowsUpdated = jdbcTemplate.update(sql, taskId);
        if(rowsUpdated>0){
            return 1;
        }else{
            throw new RuntimeException("task not deleted");
        }
    }

    public List<Tasks> incompletetask(String userId){
        String sql = "SELECT taskid, title FROM tasks WHERE user_id = ? AND completed = FALSE ORDER BY taskid ASC";
        return jdbcTemplate.query(sql, new Object[]{userId}, new BeanPropertyRowMapper<>(Tasks.class));
    }

    public List<Tasks> completedtask(String userId){
        String sql = "SELECT taskid, title FROM tasks WHERE user_id = ? AND completed = TRUE ORDER BY taskid ASC";
        return jdbcTemplate.query(sql, new Object[]{userId}, new BeanPropertyRowMapper<>(Tasks.class));
    }

}


