package ua.pytaichuk.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.Cascade;

import java.util.Objects;

@Entity
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Only alphanumeric characters allowed")
    @NotBlank(message = "Name should not be empty")
    @Size(min = 2, max = 35, message = "Name should be between 2 and 35 characters")
    private String name;

    @Column(name = "surname")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Only alphanumeric characters allowed")
    @NotBlank(message = "Surname should not be empty")
    @Size(min = 2, max = 35, message = "Surname should be between 2 and 35 characters")
    private String surname;

    @Column(name = "age")
    @Min(value = 0, message = "Age should be greater than 0")
    private int age;

    @Column(name = "email")
    @NotBlank(message = "Email should not be empty")
    @Email(message = "Email should be valid")
    private String email;

    @ManyToOne()
    @JoinColumn(name = "admin_id", referencedColumnName = "id")
    private Admin admin;

    @ManyToOne()
    @JoinColumn(name = "group_id", referencedColumnName = "id")
    private Group group;


    public Person(int id, String name, String surname, int age, String email, Admin admin, Group group) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.email = email;
        this.admin = admin;
        this.group = group;
    }

    public Person(){}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id == person.id && age == person.age && Objects.equals(name, person.name) && Objects.equals(surname, person.surname) && Objects.equals(email, person.email) && Objects.equals(admin, person.admin) && Objects.equals(group, person.group);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, age, email, admin, group);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
