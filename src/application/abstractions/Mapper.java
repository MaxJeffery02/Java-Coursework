package application.abstractions;

public interface Mapper<TSource, TResult> {
    TResult map(TSource source);
}