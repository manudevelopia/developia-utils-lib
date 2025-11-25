package info.developia.lib.value.tries;

import java.util.function.Function;
import java.util.function.Supplier;

public class TryLazy<T> {
    private final Supplier<T> supplier;
    private T defaultValue;

    public TryLazy(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    public T get() {
        try {
            return supplier.get();
        } catch (Exception ignored) {

        }
        return defaultValue == null ? null : defaultValue;
    }

    public TryLazy<T> orElse(T defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

    public <R> TryLazy<R> map(Function<T, R> function) {
        return new TryLazy<>(() -> function.apply(supplier.get()));
    }
}
