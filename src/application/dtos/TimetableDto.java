package application.dtos;

import java.util.List;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

public record TimetableDto(String name, List<TimetableEntry> timetableEntries) {

    public record TimetableEntry(
            String moduleName,
            DayOfWeek day,
            LocalTime startTime,
            LocalTime endTime
    ) {}
}
