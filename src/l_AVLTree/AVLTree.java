package l_AVLTree;

import g_Map.Map;

import java.util.ArrayList;

public class AVLTree<K extends Comparable<K>, V> implements Map<K, V> {
    private class Node {
        public K key;
        public V value;
        public Node left, right;
        public int height;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            left = null;
            right = null;
            height = 1;
        }
    }

    private Node root;
    private int size;

    public AVLTree() {
        root = null;
        size = 0;
    }

    //获得节点node的高度
    private int getHeight(Node node) {
        if (node == null) return 0;
        return node.height;
    }

    //获得节点node的平衡因子
    private int getBalanceFactor(Node node) {
        if (node == null) return 0;
        return getHeight(node.left) - getHeight(node.right);
    }

    //判断该二叉树是否是一颗二分搜索树
    public boolean isBST() {
        ArrayList<K> keys = new ArrayList<>();
        inOrder(root, keys);
        for (int i = 1; i < keys.size(); i++)
            if (keys.get(i - 1).compareTo(keys.get(i)) > 0)
                return false;

        return true;
    }

    //二分搜索树中序遍历
    private void inOrder(Node node, ArrayList<K> keys) {
        if (node == null) return;

        inOrder(node.left, keys);
        keys.add(node.key);
        inOrder(node.right, keys);
    }

    //判断该二叉树是否是一颗平衡二叉树
    public boolean isBalanced() {
        return isBalanced(root);
    }

    private boolean isBalanced(Node node) {
        if (node == null) return true;
        int balanceFactor = getBalanceFactor(node);
        if (Math.abs(balanceFactor) > 1)
            return false;
        return isBalanced(node.left) && isBalanced(node.right);
    }

    //对节点y进行右旋操作,返回旋转后新的根节点x
    //           y                                  x
    //         /  \                               /   \
    //        x   T4      向右旋转(y)            z     y
    //       / \         --------------->       / \   / \
    //      z   T3                            T1  T2 T3  T4
    //     / \
    //   T1  T2
    private Node rightRotate(Node y) {
        Node x = y.left;
        Node T3 = x.right;

        // 右旋过程
        x.right = y;
        y.left = T3;

        //更新height
        //由于x的高度与y有关,所以必须先更新y的高度
        y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;
        x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;
        return x;
    }

    //对节点y进行左旋操作,返回旋转后新的根节点x
    //           y                                  x
    //         /  \                               /   \
    //        T1   x      向左旋转(y)            z     y
    //            / \     --------------->       / \   / \
    //           T2  z                          T1  T2 T3  T4
    //              / \
    //             T3  T4
    private Node leftRotate(Node y) {
        Node x = y.right;
        Node T2 = x.left;

        //向左旋转
        x.left = y;
        y.right = T2;

        //更新height
        //由于x的高度与y有关,所以必须先更新y的高度
        y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;
        x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;
        return x;
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

        //更新height
        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
        //计算平衡因子
        int balanceFactor = getBalanceFactor(node);

        //左右子树高度差值绝对值----即平衡因子大于1,不满足平衡二叉树性质
        if (Math.abs(balanceFactor) > 1) {
            System.out.println("unbalanced: " + balanceFactor);
        }
        //维护平衡
        //LL
        if (balanceFactor > 1 && getBalanceFactor(node.left) >= 0)//该节点左子树比右子树平衡因子大
            return rightRotate(node); //则右旋转
        //RR
        if (balanceFactor < -1 && getBalanceFactor(node.right) <= 0)
            return leftRotate(node);

        //LR
        if (balanceFactor > 1 && getBalanceFactor(node.left) < 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
        //RL
        if (balanceFactor < -1 && getBalanceFactor(node.right) > 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

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
        Node retNode;
        if (key.compareTo(node.key) < 0) {
            node.left = remove(node.left, key);
            retNode = node;
        } else if (key.compareTo(node.key) > 0) {
            node.right = remove(node.right, key);
            retNode = node;
        } else { //找到删除节点
            if (node.left == null) { //待删除节点左子树不存在的情况
                Node rightNode = node.right;
                node.right = null;
                size--;
                retNode = rightNode;
            } else if (node.right == null) {
                Node leftNode = node.left;
                node.left = null;
                size--;
                retNode = leftNode;
            } else { //具体含义见e_BST -->BST
                Node successor = minimum(node.right);
                successor.left = node.left;
                successor.right = remove(node.right, successor.key);  //删除最小值
                node.left = node.right = null;
                retNode = successor;
            }
        }

        if (retNode == null) return null; //避免叶子树为空时引起空指针异常

        //更新height
        retNode.height = 1 + Math.max(getHeight(retNode.left), getHeight(retNode.right));
        //计算平衡因子
        int balanceFactor = getBalanceFactor(retNode);

        //维护平衡
        //LL
        if (balanceFactor > 1 && getBalanceFactor(retNode.left) >= 0)//该节点左子树比右子树平衡因子大
            return rightRotate(retNode); //则右旋转
        //RR
        if (balanceFactor < -1 && getBalanceFactor(retNode.right) <= 0)
            return leftRotate(retNode);

        //LR
        if (balanceFactor > 1 && getBalanceFactor(retNode.left) < 0) {
            retNode.left = leftRotate(retNode.left);
            return rightRotate(retNode);
        }
        //RL
        if (balanceFactor < -1 && getBalanceFactor(retNode.right) > 0) {
            retNode.right = rightRotate(retNode.right);
            return leftRotate(retNode);
        }

        return retNode;
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
