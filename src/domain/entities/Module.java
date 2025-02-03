package domain.entities;

import domain.abstractions.Entity;

public class Module extends Entity {
    private String name;

    public Module(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
