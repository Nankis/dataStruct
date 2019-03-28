package d_LinkedList;

public class LinkedList<E> {
    private class Node {
        public E e;
        public Node next;

        public Node(E e, Node next) {
            this.e = e;
            this.next = next;
        }

        public Node(E e) {
            this(e, null);
        }

        public Node() {
            this(null, null);
        }

        @Override
        public String toString() {
            return e.toString();
        }
    }

    private Node dummyHead; //设置虚拟头结点
    private int size;

    public LinkedList() {
        dummyHead = new Node(null, null);  //对于一个空的链表来说也存在一个虚拟头结点
        size = 0;
    }

    //获取链表中元素的个数
    public int getSize() {
        return size;
    }

    //返回链表是否为空
    public boolean isEmpty() {
        return size == 0;
    }


    //在链表的index(0~based)位置添加新元素
    //该操作在链表的实际中很少用到,主要用于练习;
    public void add(int index, E e) {
        if (index < 0 || index > size)
            throw new IllegalArgumentException("Add failed. Illegal index.");

        Node prev = dummyHead;   //dumyHead一开始指向的是0这个索引之前的元素
        for (int i = 0; i < index; i++)         //所以此时index不再需要-1
            prev = prev.next;

//            Node node = new Node(e);
//            node.next = prev.next;
//            prev.next = node;

        prev.next = new Node(e, prev.next);  //等价于上面三行代码
        size++;
    }

    public void addFirst(E e) {
        add(0, e);
    }


    //在链表末尾添加一个节点
    public void addLast(E e) {
        add(size, e);
    }

    //非常用操作, 练习用
    public E get(int index) {
        if (index < 0 || index > size)
            throw new IllegalArgumentException("Get failed. Illegal index.");
        Node cur = dummyHead.next; //从索引为0的节点开始遍历
        for (int i = 0; i < index; i++) {
            cur = cur.next;
        }
        return cur.e;
    }

    public E getFirst() {
        return get(0);
    }

    public E getLast() {
        return get(size - 1);
    }

    //修改index位置的元素 练习用
    public void set(int index, E e) {
        if (index < 0 || index > size)
            throw new IllegalArgumentException("Set failed. index Illegal.");
        Node cur = dummyHead.next;
        for (int i = 0; i < index; i++)
            cur = cur.next;

        cur.e = e;
    }

    //查找是否存在元素e
    public boolean contains(E e) {
        Node cur = dummyHead.next;
        while (cur != null) {
            if (cur.equals(e))
                return true;
            cur = cur.next;
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
//        Node cur = dummyHead.next;
//        while (cur != null) {
//            res.append(cur + "->");
//            cur = cur.next;
//        }

        //另一种写法
        for (Node cur = dummyHead.next; cur != null; cur = cur.next)
            res.append(cur + "->");
        res.append("NULL");
        return res.toString();
    }

    public E remove(int index) {
        if (index < 0 || index > size)
            throw new IllegalArgumentException("Remove failed.Illegal index");
        Node prev = dummyHead;
        for (int i = 0; i < index; i++) {
            prev = prev.next;
        }
        Node delNode = prev.next;  //要删除的节点
        prev.next = delNode.next;
        delNode.next = null;
        size--;

        return delNode.e;
    }

    public E removeFirst() {
        return remove(0);
    }

    public E removeLast() {
        return remove(size - 1);
    }

    // 从链表中删除元素e
    public void removeElement(E e) {

        Node prev = dummyHead;
        while (prev.next != null) {
            if (prev.next.e.equals(e))
                break;
            prev = prev.next;
        }

        if (prev.next != null) {
            Node delNode = prev.next;
            prev.next = delNode.next;
            delNode.next = null;
        }
    }

}
