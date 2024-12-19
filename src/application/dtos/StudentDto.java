package application.dtos;

public record StudentDto(
        String firstName,
        String lastName,
        String course,
        String type,
        String employer,
        String country) {
}