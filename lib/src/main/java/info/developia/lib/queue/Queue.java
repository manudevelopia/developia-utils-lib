package info.developia.lib.queue;

public class Queue<T> {
    private QueueNode<T> first = null;
    private QueueNode<T> last = null;

    public void push(T value) {
        if (first == null) {
            first = new QueueNode<>(value);
        } else if (first.next() == null) {
            last = new QueueNode<>(value);
            first.next(last);
        } else {
            var newLast = new QueueNode<>(value);
            last.next(newLast);
            last = newLast;
        }
    }

    public T pop() {
        if (first == null) return null;
        var value = first.value();
        first = first.next();
        return value;
    }
}
