package application.abstractions;

import application.results.Result;

public interface QueryHandler<TQuery extends Query<TResponse>, TResponse> {
    Result<TResponse> handle(TQuery query);
}