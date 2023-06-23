package ua.pytaichuk.models;

import jakarta.validation.constraints.*;

public class Person {
    private int id;

    @Pattern(regexp = "^[a-zA-Z]+$", message = "Only alphanumeric characters allowed")
    @NotBlank(message = "Name should not be empty")
    @Size(min = 2, max = 35, message = "Name should be between 2 and 35 characters")
    private String name;

    @Pattern(regexp = "^[a-zA-Z]+$", message = "Only alphanumeric characters allowed")
    @NotBlank(message = "Surname should not be empty")
    @Size(min = 2, max = 35, message = "Surname should be between 2 and 35 characters")
    private String surname;

    @Min(value = 0, message = "Age should be greater than 0")
    private int age;

    @NotBlank(message = "Email should not be empty")
    @Email(message = "Email should be valid")
    private String email;

    private int groupId;
    private String groupName;


    public Person(int id, String name, String surname, int age, String email, int groupId, String groupName) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.email = email;
        this.groupId = groupId;
        this.groupName = groupName;
    }

    public Person(){}

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

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
