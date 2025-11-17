package info.developia.lib.pile;

public class Pile<T> {
    private PileNode<T> last = null;

    void push(T value) {
        last = new PileNode<>(value, last);
    }

    T pop() {
        if (last == null) return null;
        var value = last.value();
        last = last.next();
        return value;
    }
}
