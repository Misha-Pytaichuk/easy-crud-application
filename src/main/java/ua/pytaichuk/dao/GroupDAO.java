package ua.pytaichuk.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ua.pytaichuk.models.Group;

import java.util.List;

@Component
public class GroupDAO {

    final private JdbcTemplate jdbcTemplate;

    @Autowired
    public GroupDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(String group){
        jdbcTemplate.update("INSERT INTO `groups`(group_name) VALUE(?)", group);
    }

    public List<Group> show(){
        return jdbcTemplate.query("SELECT id, group_name FROM `groups`", new BeanPropertyRowMapper<>(Group.class));
    }

    public Group show(int id){
        return jdbcTemplate.query("SELECT id, group_name FROM `groups` WHERE id = ?", new BeanPropertyRowMapper<>(Group.class), id)
                .stream().findAny().orElse(null);
    }
}
