package domain.enums;

public enum StudentType {
    FULL_TIME("Full time"),
    PART_TIME("Part time"),
    APPRENTICE("Apprentice"),
    FOREIGN_EXCHANGE("Foreign exchange");

    private String name;

    StudentType(String NAME) {
        this.name = NAME;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "StudentType{}";
    }
}