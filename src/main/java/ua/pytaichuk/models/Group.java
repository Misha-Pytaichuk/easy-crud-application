package ua.pytaichuk.models;

import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Component;

public class Group {
    private int id;
    private String groupName;

    public Group(int id, String groupName) {
        this.id = id;
        this.groupName = groupName;
    }

    public Group(){}
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
