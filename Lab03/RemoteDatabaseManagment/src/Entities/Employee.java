package Entities;

import java.io.Serializable;

public class Employee implements Serializable {
    private String name;
    private int ID;

    public Employee(int ID, String name) {
        this.ID = ID;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getID() {
        return ID;
    }
}
