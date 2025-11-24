package info.developia.lib.value.tries;

import java.util.function.Supplier;

public class Try {

    public static <T> TryResult<T> of(Supplier<T> supplier) {
        try {
            return new TrySuccess<>(supplier.get());
        } catch (Throwable e) {
            return new TryFailure<>(e, supplier);
        }
    }
}
