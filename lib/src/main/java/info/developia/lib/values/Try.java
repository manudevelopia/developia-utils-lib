package info.developia.lib.values;

import java.lang.reflect.InvocationTargetException;
import java.util.function.Supplier;

public class Try {
    public interface TryResult<T> {
        boolean isSuccess();

        T get();

        T orElse(T defaultValue);

        <X extends Throwable> void orElseThrow(Class<X> exceptionClazz) throws X;

        <X extends Throwable> void orElseThrow(Supplier<? extends X> exceptionSupplier) throws X;

        TryResult<T> retries(int attempts);
    }

    public record Success<T>(T value) implements TryResult<T> {

        @Override
        public boolean isSuccess() {
            return true;
        }

        @Override
        public T get() {
            return value;
        }

        @Override
        public T orElse(T defaultValue) {
            return value;
        }

        @Override
        public <X extends Throwable> void orElseThrow(Class<X> exceptionClazz) {
        }

        @Override
        public <X extends Throwable> void orElseThrow(Supplier<? extends X> exceptionSupplier) {
        }

        @Override
        public TryResult<T> retries(int attempts) {
            return this;
        }
    }

    public record Failure<T>(Throwable throwable, Supplier<T> supplier) implements TryResult<T> {

        @Override
        public boolean isSuccess() {
            return false;
        }

        @Override
        public T get() {
            return null;
        }

        @Override
        public T orElse(T defaultValue) {
            return defaultValue;
        }

        @Override
        public <X extends Throwable> void orElseThrow(Class<X> exceptionClazz) throws X {
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
        public <X extends Throwable> void orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
            throw exceptionSupplier.get();
        }

        @Override
        public TryResult<T> retries(int attempts) {
            Throwable exception;
            do {
                try {
                    return new Success<>(supplier.get());
                } catch (Throwable e) {
                    exception = e;
                }
            }
            while (attempts-- > 0);
            return new Failure<>(exception, supplier);
        }
    }

    public static <T> TryResult<T> of(Supplier<T> supplier) {
        try {
            return new Success<>(supplier.get());
        } catch (Throwable e) {
            return new Failure<>(e, supplier);
        }
    }
}
