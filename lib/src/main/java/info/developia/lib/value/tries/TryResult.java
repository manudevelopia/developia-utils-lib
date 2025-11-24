package info.developia.lib.value.tries;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public interface TryResult<T> {
    boolean isSuccess();

    boolean isFailure();

    T get();

    <X> TryResult<X> map(Function<T, X> function);

    T orElse(T defaultValue);

    <X extends Throwable> T orElseThrow(Class<X> exceptionClazz) throws X;

    <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X;

    <X extends Throwable> T orElseThrowWithMessage(Class<X> exceptionClazz) throws X;

    TryResult<T> retries(int attempts);

    TryResult<T> onError(Consumer<Throwable> consumer);
}
