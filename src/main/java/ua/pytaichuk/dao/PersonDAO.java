package ua.pytaichuk.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ua.pytaichuk.models.Admin;
import ua.pytaichuk.models.Group;
import ua.pytaichuk.models.Person;

import java.util.List;

@Component
public class PersonDAO {

    final private JdbcTemplate jdbcTemplate;
    final private GroupDAO groupDAO;
    private Admin admin;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate, GroupDAO groupDAO) {
        this.jdbcTemplate = jdbcTemplate;
        this.groupDAO = groupDAO;
    }

    public List<Person> index(Admin admin){
        this.admin = admin;
        return jdbcTemplate.query("SELECT * FROM person WHERE admin_id = ?", new BeanPropertyRowMapper<>(Person.class), admin.getId());
    }

    public Person show(int id){
        return jdbcTemplate.query("SELECT person.id, name, surname, age, email, group_id, g.group_name  FROM person JOIN `groups` g on g.id = person.group_id  WHERE person.id = ?", new BeanPropertyRowMapper<>(Person.class), id)
                .stream().findAny().orElse(null);
    }

    public List<Person> indexByGroupId(Admin admin, int id){
        return jdbcTemplate.query("SELECT * FROM person WHERE group_id = ? AND admin_id = ?", new BeanPropertyRowMapper<>(Person.class), id, admin.getId());
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO person (name, surname, age, email, admin_id, group_id) VALUES(?, ?, ?, ?, ?, ?)", person.getName(), person.getSurname(), person.getAge(), person.getEmail(), admin.getId(), person.getGroupId());
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM person WHERE id = ?", id);
    }

    public void edit(int id, Person person) {

        jdbcTemplate.update( "UPDATE person SET name = ?, surname = ?, age = ?, email = ?, group_id = ? WHERE id = ?",
                        person.getName().trim(),
                        person.getSurname().trim(),
                        person.getAge(),
                        person.getEmail().trim(),
                        person.getGroupId(),
                        id);
    }
}
