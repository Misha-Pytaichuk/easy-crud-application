package ua.pytaichuk.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class Admin {

    private int id;
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Only alphanumeric characters allowed")
    private String login;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Only alphanumeric characters allowed")
    @Size(min = 6)
    private String password;

    private String table;


    public Admin(){}

    public Admin(int id, String login, String password, String table) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.table = table;
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

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }
}
