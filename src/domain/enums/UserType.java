package domain.enums;

public enum UserType {
    STUDENT("Student"),
    TUTOR("Tutors"),
    ADMIN("Admin");

    private String desc;

    UserType(String desc){
        this.desc = desc;
    }

    @Override
    public String toString() {
        return desc;
    }
}