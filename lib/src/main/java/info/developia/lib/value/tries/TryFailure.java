package info.developia.lib.value.tries;

import java.lang.reflect.InvocationTargetException;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public record TryFailure<T>(Throwable e, Supplier<T> supplier) implements TryResult<T> {

    @Override
    public boolean isSuccess() {
        return false;
    }

    @Override
    public T get() {
        return null;
    }

    @Override
    public <X> TryResult<X> map(Function<T, X> function) {
        return null;
    }

    @Override
    public T orElse(T defaultValue) {
        return defaultValue;
    }

    @Override
    public <X extends Throwable> T orElseThrow(Class<X> exceptionClazz) throws X {
        try {
            throw exceptionClazz.getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException |
                 InstantiationException |
                 IllegalAccessException |
                 InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
        throw exceptionSupplier.get();
    }

    @Override
    public <X extends Throwable> T orElseThrowWithMessage(Class<X> exceptionClazz) throws X {
        try {
            throw exceptionClazz.getDeclaredConstructor(String.class).newInstance(e.getMessage());
        } catch (NoSuchMethodException |
                 InstantiationException |
                 IllegalAccessException |
                 InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public TryResult<T> retries(int attempts) {
        Throwable exception;
        do {
            try {
                return new TrySuccess<>(supplier.get());
            } catch (Throwable e) {
                exception = e;
            }
        }
        while (attempts-- > 0);
        return new TryFailure<>(exception, supplier);
    }

    @Override
    public TryResult<T> onError(Consumer<Throwable> consumer) {
        consumer.accept(e);
        return this;
    }
}
