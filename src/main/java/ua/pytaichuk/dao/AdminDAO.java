package ua.pytaichuk.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ua.pytaichuk.models.Admin;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class AdminDAO {

    final private JdbcTemplate jdbcTemplate;

    @Autowired
    public AdminDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Admin admin) {

        admin.setId(getAdminId(admin));
        admin.setLogin(admin.getLogin().trim());
        admin.setTable(generateTableName());
        createTable(admin);

        jdbcTemplate.update("INSERT INTO admins(login, password, `table`) VALUES(?, ?, ?)", admin.getLogin(), admin.getPassword(), admin.getTable());
    }

    private static String generateTableName() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        return "my_table_" + currentDateTime.format(formatter);
    }

    private void createTable(Admin admin){

        jdbcTemplate.update("CREATE TABLE " + admin.getTable() + " (" +
                "id INT NOT NULL AUTO_INCREMENT,\n" +
                "name VARCHAR(40),\n" +
                "surname VARCHAR(40),\n" +
                "age INT,\n" +
                "email VARCHAR(200),\n" +
                "PRIMARY KEY(id)\n" +
                ");");

    }

    private int getAdminId(Admin admin){
        return jdbcTemplate.query("SELECT id FROM admins WHERE login = ? AND password = ?", new BeanPropertyRowMapper<>(Integer.class), admin.getLogin(), admin.getPassword())
                .stream().findAny().orElse(-1);
    }

    public Admin login(Admin admin) {

        return jdbcTemplate.query("SELECT * FROM admins WHERE login = ? AND password = ?", new BeanPropertyRowMapper<>(Admin.class), admin.getLogin(), admin.getPassword()).
                stream().findAny().orElse(null);
    }
}
