package info.developia.lib.tree;

public class BiTreeNode<T> {
    private BiTreeNode<T> left;
    private BiTreeNode<T> right;
    private final T value;

    public BiTreeNode(T value) {
        this.value = value;
    }

    public void left(BiTreeNode<T> a) {
        this.left = a;
    }

    public BiTreeNode<T> left() {
        return left;
    }

    public void right(BiTreeNode<T> b) {
        this.right = b;
    }

    public BiTreeNode<T> right() {
        return right;
    }

    public T value() {
        return value;
    }
}
