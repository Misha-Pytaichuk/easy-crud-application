package ua.pytaichuk.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "admins")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "login")
    @NotBlank(message = "The field should not be empty")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Only alphanumeric characters allowed")
    @Size(min = 4, message = "Size of login should be greater than 4")
    private String login;

    @Column(name = "password")
    @NotBlank(message = "The field should not be empty")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Only alphanumeric characters allowed")
    @Size(min = 6, message = "Size of the password should be greater than 6")
    private String password;

    @OneToMany(mappedBy = "admin")
    private List<Person> people;


    public Admin(){}

    public Admin(int id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Admin admin = (Admin) o;
        return id == admin.id && Objects.equals(login, admin.login) && Objects.equals(password, admin.password) && Objects.equals(people, admin.people);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, people);
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Person> getPeople() {
        return people;
    }

    public void setPeople(List<Person> people) {
        this.people = people;
    }

}
