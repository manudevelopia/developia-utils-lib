package info.developia.lib.value;

import java.util.function.Supplier;

public class Lazy<R> {
    private final Supplier<R> function;
    private boolean isEvaluated;
    private R value;

    public Lazy(Supplier<R> function) {
        this.function = function;
    }

    public static <R> Lazy<R> of(Supplier<R> function) {
        return new Lazy<>(function);
    }

    public R get() {
        if (isEvaluated) return value;
        var result = Try.of(function);
        isEvaluated = result.isSuccess();
        value = result.get();
        return value;
    }

    public R getOrElse(R defaultValue) {
        if (isEvaluated) return value;
        value = Try.of(function).orElse(defaultValue);
        return value;
    }
}
