package g_Map;

public class BSTMap<K extends Comparable<K>, V> implements Map<K, V> {
    private class Node {
        public K key;
        public V value;
        public Node left, right;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            left = null;
            right = null;
        }
    }

    private Node root;
    private int size;

    public BSTMap() {
        root = null;
        size = 0;
    }

    @Override
    public void add(K key, V value) {
        root = add(root, key, value);
    }

    private Node add(Node node, K key, V value) {
        if (node == null) {
            size++;
            return new Node(key, value);
        }
        if (key.compareTo(node.key) < 0) {
            node.left = add(node.left, key, value);
        } else if (key.compareTo(node.key) > 0) {
            node.right = add(node.right, key, value);
        } else //key.compareTo(node.key)==0  重复key则修改原有value
            node.value = value;
        return node;
    }

    //返回以Node为节点的二分搜索树中,key所在的节点
    private Node getNode(Node node, K key) {
        if (node == null)
            return null;

        if (key.compareTo(node.key) == 0)
            return node;
        if (key.compareTo(node.key) < 0)
            return getNode(node.left, key);
        else return getNode(node.right, key);
    }


    private Node minimum(Node node) {
        if (size == 0)
            throw new IllegalArgumentException("BST is empty.");

        if (node.left == null)
            return node;
        return minimum(node.left);
    }

    //从二分搜索树中删除最小值所在节点,返回最小值
    public Node removeMin(Node node) {
        if (node.left == null) {
            Node rightNode = node.right;
            node.right = null;
            size--;
            return rightNode;
        }
        node.left = removeMin(node.left);
        return node;
    }


    //从二分搜索树中删除键为key的节点
    @Override
    public V remove(K key) {
        Node node = getNode(root, key);
        if (node != null) {
            root = remove(root, key);
            return node.value;
        }
        return null;
    }

    //删除以node为根的二分搜索树中键为key的节点,递归算法
    //返回删除节点后新的二分搜索树的根
    private Node remove(Node node, K key) {
        if (node == null)
            return null;
        if (key.compareTo(node.key) < 0) {
            node.left = remove(node.left, key);
            return node;
        } else if (key.compareTo(node.key) > 0) {
            node.right = remove(node.right, key);
            return node;
        } else { //找到删除节点
            if (node.left == null) { //待删除节点左子树不存在的情况
                Node rightNode = node.right;
                node.right = null;
                size--;
                return rightNode;
            } else if (node.right == null) {
                Node leftNode = node.left;
                node.left = null;
                size--;
                return leftNode;
            } else { //具体含义见e_BST -->BST
                Node successor = minimum(node.right);
                successor.left = node.left;
                successor.right = removeMin(node.right);
                node.left = node.right = null;
                return successor;
            }

        }

    }


    @Override
    public boolean contains(K key) {
        return getNode(root, key) != null;
    }

    @Override
    public V get(K key) {
        Node node = getNode(root, key);
        return node == null ? null : node.value;
    }

    @Override
    public void set(K key, V newValue) {
        Node node = getNode(root, key);
        if (node == null)
            throw new IllegalArgumentException(key + " doesn't exist!");
        node.value = newValue;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }


}
