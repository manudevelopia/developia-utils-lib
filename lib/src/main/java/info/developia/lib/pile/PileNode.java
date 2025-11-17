package info.developia.lib.pile;

public record PileNode<T>(
        T value,
        PileNode<T> next
) {
}
