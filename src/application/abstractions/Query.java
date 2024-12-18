package application.abstractions;

/**
 * The Query interface serves as a marker interface for queries in the application.
 *
 * A query represents a request for data or information, typically without any side effects.
 * The generic type T represents the type of the data that the query will return when handled.
 *
 * This interface can be extended by different types of query objects that specify the data
 * or information they request, providing the necessary structure for handling the query
 * and returning the appropriate response.
 *
 * @param <T> The type of data that the query returns once handled.
 */
public interface Query<T> {
}
