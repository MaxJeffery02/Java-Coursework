package domain.entities;

import domain.abstractions.Entity;

public class Course extends Entity {
    private String name;

    public Course(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}