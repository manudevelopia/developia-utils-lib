package info.developia.lib;

import java.util.function.Supplier;

public class Lazy<R> {
    private final Supplier<R> function;

    public Lazy(Supplier<R> function) {
        this.function = function;
    }

    public static <R> Lazy<R> of(Supplier<R> function) {
        return new Lazy<>(function);
    }

    public R get() {
        return function.get();
    }
}
