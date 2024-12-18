package application.abstractions;

/**
 * A generic interface for mapping an object of type TSource to an object of type TResult.
 * This is used to transform data from one type to another, typically in the context of DTOs and entities.
 *
 * @param <TSource> The source type that needs to be mapped.
 * @param <TResult> The result type after mapping.
 */
public interface Mapper<TSource, TResult> {

    /**
     * Maps the given source object to a result of type TResult.
     *
     * @param source The object to be mapped.
     * @return The mapped object of type TResult.
     */
    TResult map(TSource source);
}
