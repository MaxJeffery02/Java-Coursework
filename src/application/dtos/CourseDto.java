package application.dtos;

import java.util.List;
import java.util.UUID;

public record CourseDto(UUID id, String name, String modules) {
}