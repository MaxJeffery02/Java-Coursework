package domain.entities;

import domain.abstractions.Entity;

import java.util.ArrayList;
import java.util.List;

public class Course extends Entity {
    private String name;
    private List<Module> modules;

    public Course(String name) {
        this.name = name;
        this.modules = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Module> getModules() {
        return modules;
    }

    public void addModule(Module module) {
        if (this.modules == null) {
            this.modules = new ArrayList<>();
        }

        this.modules.add(module);
    }
}
