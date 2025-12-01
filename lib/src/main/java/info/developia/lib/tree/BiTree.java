package info.developia.lib.tree;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BiTree<T> {
    private BiTreeNode<T> root;
    private final Comparator<T> comparator;

    public BiTree(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    public void push(T value) {
        var newNode = new BiTreeNode<>(value);
        if (root == null) root = newNode;
        else compareNodes(root, newNode);
    }

    private void compareNodes(BiTreeNode<T> nodeA, BiTreeNode<T> nodeB) {
        if (comparator.compare(nodeA.value(), nodeB.value()) > 0) {
            if (nodeA.left() != null) compareNodes(nodeA.left(), nodeB);
            else {
                nodeA.left(nodeB);
                nodeB.parent(nodeA);
            }
        } else {
            if (nodeA.right() != null) compareNodes(nodeA.right(), nodeB);
            else {
                nodeA.right(nodeB);
                nodeB.parent(nodeA);
            }
        }
    }

    public List<T> inOrder() {
        var values = new ArrayList<T>();
        getValuesInOrder(root, values);
        return values;
    }

    private void getValuesInOrder(BiTreeNode<T> node, List<T> values) {
        if (node.left() != null) getValuesInOrder(node.left(), values);
        values.add(node.value());
        if (node.right() != null) getValuesInOrder(node.right(), values);
    }

    public List<T> preOrder() {
        var values = new ArrayList<T>();
        getValuesPreOrder(root, values);
        return values;
    }

    private void getValuesPreOrder(BiTreeNode<T> node, List<T> values) {
        values.add(node.value());
        if (node.left() != null) getValuesPreOrder(node.left(), values);
        if (node.right() != null) getValuesPreOrder(node.right(), values);
    }

    public List<T> postOrder() {
        var values = new ArrayList<T>();
        getValuesPostOrder(root, values);
        return values;
    }

    private void getValuesPostOrder(BiTreeNode<T> node, List<T> values) {
        if (node.left() != null) getValuesPostOrder(node.left(), values);
        if (node.right() != null) getValuesPostOrder(node.right(), values);
        values.add(node.value());
    }

    public T popInOrder() {
        if (root == null) return null;
        var node = getMinimalInOrder(root);
        return node.value();
    }

    private BiTreeNode<T> getMinimalInOrder(BiTreeNode<T> node) {
        if (node.left() != null) return getMinimalInOrder(node.left());
        if (node.parent() == null) {
            var rootNode = new BiTreeNode<>(node.value());
            root = null;
            return rootNode;
        }
        if (node.right() != null) {
            node.right().parent(node.parent());
            node.parent().left(node.right());
        } else node.parent().left(null);
        return node;
    }
}
