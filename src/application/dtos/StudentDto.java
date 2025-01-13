package application.dtos;

public record StudentDto(
        String firstName,
        String lastName,
        String email,
        String course,
        String type,
        String employer,
        String country) {
}