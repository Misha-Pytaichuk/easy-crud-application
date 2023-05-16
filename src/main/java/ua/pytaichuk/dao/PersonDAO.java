package ua.pytaichuk.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ua.pytaichuk.models.Admin;
import ua.pytaichuk.models.Person;

import java.sql.*;
import java.util.List;

@Component
public class PersonDAO {

    final private JdbcTemplate jdbcTemplate;
    private Admin admin;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index(Admin admin){
        this.admin = admin;
        return jdbcTemplate.query("SELECT * FROM " + admin.getTable(), new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int id){
        return jdbcTemplate.query("SELECT * FROM " + admin.getTable() + " WHERE id = ?", new BeanPropertyRowMapper<>(Person.class), id)
                .stream().findAny().orElse(null);
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO " + admin.getTable() + " (name, surname, age, email) VALUES(?, ?, ?, ?)", person.getName(), person.getSurname(), person.getAge(), person.getEmail());
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM " + admin.getTable() + " WHERE id = ?", id);
    }

    public void edit(int id, Person person) {

        jdbcTemplate.update( "UPDATE " + admin.getTable() + " SET name = ?, surname = ?, age = ?, email = ? WHERE id = ?",
                        person.getName().trim(),
                        person.getSurname().trim(),
                        person.getAge(),
                        person.getEmail().trim(),
                        id);
    }
}
