package info.developia.lib.queue;

public class QueueNode<T> {
    private final T value;
    private QueueNode<T> next;

    public QueueNode(T value) {
        this.value = value;
    }

    public T value() {
        return value;
    }

    public QueueNode<T> next() {
        return next;
    }

    public void next(QueueNode<T> next) {
        this.next = next;
    }
}
