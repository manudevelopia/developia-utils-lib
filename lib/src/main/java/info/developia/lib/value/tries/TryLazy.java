package info.developia.lib.value.tries;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class TryLazy<T> {
    private final Supplier<T> supplier;
    private T defaultValue;
    private int attempts;
    private Supplier<? extends RuntimeException> orExceptionSupplier;
    private Consumer<Throwable> errorConsumer;

    public TryLazy(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    public T get() {
        do {
            try {
                return supplier.get();
            } catch (Throwable e) {
                if (errorConsumer != null) errorConsumer.accept(e);
            }
        }
        while (attempts-- > 0);
        if (orExceptionSupplier != null) throw orExceptionSupplier.get();
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

    public TryLazy<T> orElseThrow(Supplier<? extends RuntimeException> orExceptionSupplier) {
        this.orExceptionSupplier = orExceptionSupplier;
        return this;
    }

    public TryLazy<T> onError(Consumer<Throwable> errorConsumer) {
        this.errorConsumer = errorConsumer;
        return null;
    }
}
