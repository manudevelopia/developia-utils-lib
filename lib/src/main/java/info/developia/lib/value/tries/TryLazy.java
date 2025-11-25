package info.developia.lib.value.tries;

import java.util.function.Function;
import java.util.function.Supplier;

public class TryLazy<T> {
    private final Supplier<T> supplier;
    private T defaultValue;
    private int attempts;

    public TryLazy(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    public T get() {
        do {
            try {
                return supplier.get();
            } catch (Throwable ignored) {
            }
        }
        while (attempts-- > 0);
        return defaultValue == null ? null : defaultValue;
    }

    public TryLazy<T> orElse(T defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

    public <R> TryLazy<R> map(Function<T, R> function) {
        return new TryLazy<>(() -> function.apply(supplier.get()));
    }

    public TryLazy<T> retries(int retries) {
        this.attempts = retries;
        return this;
    }
}
