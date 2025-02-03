package application.mappers;

import application.abstractions.Mapper;
import application.dtos.CourseDto;
import domain.entities.Course;
import domain.entities.Module;
import domain.entities.Tutor;

import java.util.stream.Collectors;

public class CourseDtoMapper implements Mapper<Course, CourseDto> {
    @Override
    public CourseDto map(Course course) {

        String modules = course.getModules() == null
                ? ""
                : course.getModules()
                    .stream()
                    .map(Module::getName)
                    .collect(Collectors.joining(", "));

        return new CourseDto(course.getId(), course.getName(), modules);
    }
}
