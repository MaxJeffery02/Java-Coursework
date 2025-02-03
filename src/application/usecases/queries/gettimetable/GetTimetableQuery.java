package application.usecases.queries.gettimetable;

import application.abstractions.Query;
import application.dtos.TimetableDto;

import java.util.UUID;

public record GetTimetableQuery(UUID userId) implements Query<TimetableDto> {
}
