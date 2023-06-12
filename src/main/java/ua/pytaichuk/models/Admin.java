package ua.pytaichuk.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class Admin {

    private int id;
    @NotBlank(message = "The field should not be empty")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Only alphanumeric characters allowed")
    @Size(min = 4, message = "Size of login should be greater than 4")
    private String login;

    @NotBlank(message = "The field should not be empty")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Only alphanumeric characters allowed")
    @Size(min = 6, message = "Size of the password should be greater than 6")
    private String password;

    public Admin(){}

    public Admin(int id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
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
}
