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
        public <X extends Throwable> void orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {

        }

        @Override
        public T recover(Throwable exception) {
            return null;
        }
    }

    public record Failure<T>(Throwable throwable) implements TryResult<T> {
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
    }

    public static <T> TryResult<T> of(Supplier<T> supplier) {
        try {
            return new Success<>(supplier.get());
        } catch (Throwable e) {
            return new Failure<>(e);
        }
    }
}
