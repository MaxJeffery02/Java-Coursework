package application.dtos;

public record TutorDto(
        String firstName,
        String lastName,
        String email,
        String coursesTaught) { }