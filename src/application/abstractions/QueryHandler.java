package application.abstractions;

import application.results.Result;

/**
 * The QueryHandler interface defines the contract for handling queries that return a response.
 *
 * A QueryHandler is responsible for processing a query and returning a result of type TResponse.
 * The result is wrapped in a Result object, which encapsulates both the success/failure outcome
 * and any potential data or error messages.
 *
 * @param <TQuery> The type of the query object that this handler processes.
 * @param <TResponse> The type of the response returned after handling the query.
 */
public interface QueryHandler<TQuery extends Query<TResponse>, TResponse> {

    /**
     * Handles the given query and returns a result.
     *
     * This method is responsible for processing the query, performing the necessary
     * actions (e.g., data retrieval, computations), and returning the result.
     * The result is encapsulated in a Result object, which provides information on
     * the success or failure of the operation.
     *
     * @param query The query object that holds the information needed to execute the request.
     * @return A Result object that encapsulates the response of type TResponse.
     */
    Result<TResponse> handle(TQuery query);
}
