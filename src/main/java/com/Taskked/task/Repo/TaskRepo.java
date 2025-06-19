package com.Taskked.task.Repo;

import com.Taskked.task.Model.Tasks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

@Repository
public class TaskRepo {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Tasks> alltaskd(String userId){
        String sql = "SELECT taskid, title, completed FROM tasks WHERE user_id = ?"; // Added 'completed' to SELECT
        return jdbcTemplate.query(sql, new Object[]{userId}, new BeanPropertyRowMapper<>(Tasks.class));
    }

    public int addTasks(Tasks tasks) {
        String sql = "INSERT INTO tasks (title, completed, user_id) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        int rowsInserted = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, tasks.getTitle());
            ps.setBoolean(2, tasks.isCompleted());
            ps.setString(3, tasks.getUserId());
            return ps;
        }, keyHolder);

        if (rowsInserted > 0) {
            Map<String, Object> keys = keyHolder.getKeys();
            if (keys != null && keys.containsKey("taskid")) {
                Number key = (Number) keys.get("taskid");
                tasks.setTaskid(key.longValue());
                return Math.toIntExact(key.longValue());
            } else {
                throw new RuntimeException("Failed to retrieve task ID");
            }
        } else {
            throw new RuntimeException("Task not added");
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

    public Tasks getTaskById(long taskid) {
        String sql = "SELECT * FROM tasks WHERE taskid = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Tasks.class), taskid);
    }


}


