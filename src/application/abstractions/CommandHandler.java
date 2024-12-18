package application.abstractions;

import application.results.Result;

public interface CommandHandler<TCommand extends Command<TResponse>, TResponse> {
    Result<TResponse> handle(TCommand command);
}
