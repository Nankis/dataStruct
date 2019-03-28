package e_BST;


import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

//二分搜索树  --本例不包含重复元素
public class BST<E extends Comparable<E>> {  //所存元素类型必须是具有可比较大小的性质
    private class Node {
        public E e;
        public Node left, right;

        public Node(E e) {
            this.e = e;
            left = null;
            right = null;
        }
    }

    private Node root;
    private int size;

    public BST() {
        root = null;
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    //向二分搜索树中添加新的元素e
    public void add(E e) {
        root = add(root, e);
    }

    //向以node为根的二分搜索树中插入元素e,递归算法
    //返回插入新节点后的二分搜索树的根
    private Node add(Node node, E e) {
        if (node == null) {
            size++;
            return new Node(e);
        }
        //以上为递归的基础条件

        if (e.compareTo(node.e) < 0)
            node.left = add(node.left, e);
        else if (e.compareTo(node.e) > 0)  //重复的e会直接return
            node.right = add(node.right, e);
        return node;
    }

    //查找二分搜索树是否包含元素e
    public boolean contains(E e) {
        return contains(root, e);
    }

    private boolean contains(Node node, E e) {
        if (node == null) return false;
        else if (e.compareTo(node.e) == 0) return true;

        else if (e.compareTo(node.e) < 0)
            return contains(node.left, e);
        else
            return contains(node.right, e);

    }

    //前序遍历
    public void preOrder() {
        preOrder(root);
    }

    private void preOrder(Node node) {
        if (node == null) return;

        System.out.println(node.e);
        preOrder(node.left);
        preOrder(node.right);
    }

    //中序遍历
    public void inOrder() {
        inOrder(root);
    }

    private void inOrder(Node node) {
        if (node == null) return;

        inOrder(node.left);
        System.out.println(node.e);
        inOrder(node.right);
    }

    //后序遍历
    public void postOrder() {
        postOrder(root);
    }

    private void postOrder(Node node) {
        if (node == null) return;

        postOrder(node.left);
        postOrder(node.right);
        System.out.println(node.e);
    }

    //二叉树的前序遍历  非递归算法
    public void preOrderNR() {
        Stack<Node> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            Node cur = stack.pop();
            System.out.println(cur.e);

            if (cur.right != null)
                stack.push(cur.right);
            if (cur.left != null)
                stack.push(cur.left);
        }
    }

    //二分搜索树的层序遍历  非递归算法
    public void levelOrder() {
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node cur = queue.remove();
            System.out.println(cur.e);

            if (cur.left != null)
                queue.add(cur.left);
            if (cur.right != null)
                queue.add(cur.right);
        }

    }

    //二分搜索树的最小值
    public E minimum() {
        return minimum(root).e;
    }

    private Node minimum(Node node) {
        if (size == 0)
            throw new IllegalArgumentException("BST is empty.");

        if (node.left == null)
            return node;
        return minimum(node.left);
    }

    //从二分搜索树中删除最小值所在节点,返回最小值
    public E removeMin() {
        E ret = minimum();
        root = removeMin(root);
        return ret;
    }

    //删除掉以node为根的二分搜索树中最小节点
    //返回删除节点后新的二分搜索树的根
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

    //二分搜索树的最大值
    public E maximum() {
        return maximum(root).e;
    }

    private Node maximum(Node node) {
        if (size == 0)
            throw new IllegalArgumentException("BST is empty.");

        if (node.right == null)
            return node;
        return maximum(node.right);

    }

    //删除以node为根的二分搜索树中最大节点
    //返回删除节点后新的二分搜索树根
    public E removeMax() {
        E ret = maximum();
        root = removeMax(root);
        return ret;
    }

    private Node removeMax(Node node) {
        if (node.right == null) {
            Node leftNode = node.left;
            node.left = null;
            size--;
            return leftNode;
        }
        node.right = removeMin(node.right);
        return node;

    }

    public void remove(E e) {
        root = remove(root, e);
    }

    private Node remove(Node node, E e) {
        if (node == null)
            return null;

        if (e.compareTo(node.e) < 0) { //如果待删除的e比当前节点的e值更小则递归往左边查找
            node.left = remove(node.left, e);
            return node;
        }
        if (e.compareTo(node.e) > 0) { //如果待删除的e比当前节点的e值更大则递归往右边查找
            node.right = remove(node.right, e);
            return node;
        } else { //e == node.e  待删除e正好等于当前节点e
            if (node.left == null) { //此时待删除节点的左子树不存在
                Node rightNode = node.right;
                node.right = null;  //
                size--;
                return rightNode;
            } else if (node.right == null) {  //此时待删除节点的右子树不存在
                Node leftNode = node.left;
                node.left = null;
                size--;
                return leftNode;
            } else {
                //1.待删除节点左右子树均存在的情况
                // 2.找到比待删除节点大的最小节点---即待删除节点右子树的最小节点
                // 3.用这个节点顶替待删除节点的位置
                Node successor = minimum(node.right);  //successor是待删除节点的后继节点,也可以用左子树的最大值(前驱)最大值
                successor.right = removeMin(node.right); //将successor在原先位置删除掉然后与其右子树对接上
//                size ++;   //这里不再需要动size 1.创建的successor其实和removeMin相当于交换位置..
                successor.left = node.left;
                node.left = node.right = null;
//                size--;
                return successor;

            }


        }
    }


}
