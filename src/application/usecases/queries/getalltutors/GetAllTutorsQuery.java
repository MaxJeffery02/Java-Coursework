package application.usecases.queries.getalltutors;

import application.abstractions.Query;
import application.dtos.TutorDto;

import java.util.List;

public record GetAllTutorsQuery() implements Query<List<TutorDto>>{}