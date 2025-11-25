package info.developia.lib.value.tries;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static info.developia.lib.value.tries.Try.of;

public record TrySuccess<T>(T value) implements TryResult<T> {

    @Override
    public boolean isSuccess() {
        return true;
    }

    @Override
    public boolean isFailure() {
        return !isSuccess();
    }

    @Override
    public T get() {
        return value;
    }

    @Override
    public <X> TryResult<X> map(Function<T, X> function) {
        return of(() -> function.apply(value));
    }

    @Override
    public T orElse(T defaultValue) {
        return value;
    }

    @Override
    public <X extends Throwable> T orElseThrow(Class<X> exceptionClazz) {
        return value;
    }

    @Override
    public <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) {
        return value;
    }

    @Override
    public <X extends Throwable> T orElseThrowWithMessage(Class<X> exceptionClazz) {
        return value;
    }

    @Override
    public TryResult<T> retries(int attempts) {
        return this;
    }

    @Override
    public TryResult<T> onError(Consumer<Throwable> consumer) {
        return this;
    }
}
