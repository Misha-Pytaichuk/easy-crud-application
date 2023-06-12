package ua.pytaichuk.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ua.pytaichuk.models.Admin;

import java.util.Optional;

@Component
public class AdminDAO {

    final private JdbcTemplate jdbcTemplate;
    @Autowired
    public AdminDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Admin admin) {
        admin.setLogin(admin.getLogin().trim());
        admin.setPassword(admin.getPassword().trim());

        jdbcTemplate.update("INSERT INTO admins(login, password) VALUES(?, ?)", admin.getLogin(), admin.getPassword());
    }

    public Optional<Admin> show(String login){
        return jdbcTemplate.query("SELECT * FROM admins WHERE login = ?", new BeanPropertyRowMapper<>(Admin.class), login)
                .stream()
                .findAny();
    }

    public Admin login(Admin admin) {
        return jdbcTemplate.query("SELECT * FROM admins WHERE login = ? AND password = ?", new BeanPropertyRowMapper<>(Admin.class), admin.getLogin(), admin.getPassword())
                .stream()
                .findAny()
                .orElse(null);
    }
}
