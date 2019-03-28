package h_MaxHeap;

import a_arry.Array;

public class MaxHeap<E extends Comparable<E>> {
    private Array<E> data;

    public MaxHeap(int capacity) {
        data = new Array<>(capacity);
    }

    public MaxHeap() {
        data = new Array<>();
    }

    public MaxHeap(E[] arr) {
        data = new Array<>(arr);
        for (int i = parent(arr.length - 1); i >= 0; i--)
            siftDown(i);

    }

    //返回堆中元素的个数
    public int size() {
        return data.getSize();
    }

    //返回一个布尔值,表示堆中是否为空
    public boolean isEmpty() {
        return data.isEmpty();
    }

    //返回完全二叉树的数组表示中, 一个索引所表示的元素的父亲节点的索引
    private int parent(int index) {
        if (index == 0)
            throw new IllegalArgumentException("index-0 doesn't have parent.");
        return (index - 1) / 2;  //数组0索引不为空的公式
    }

    //返回完全二叉树的数组表示中, 一个索引所表示的元素的左孩子节点的索引
    private int leftChild(int index) {
        return index * 2 + 1;
    }

    //返回完全二叉树的数组表示中, 一个索引所表示的元素的右孩子节点的索引
    private int rightChild(int index) {
        return index * 2 + 2;
    }

    //向堆中添加元素
    public void add(E e) {
        data.addLast(e);
        siftUp(data.getSize() - 1);//传入一个索引
    }

    //传入一个索引,使其对应的值满足比父节点小----保证满足堆的性质
    private void siftUp(int k) {
        //如果当前加入的元素值比父节点大,那么其值和父节点交换,但索引不变
        while (k > 0 && data.get(parent(k)).compareTo(data.get(k)) < 0) {
            data.swap(k, parent(k));
            k = parent(k);
        }
    }

    //查看堆中最大的元素
    public E findMax() {
        if (data.getSize() == 0) throw new IllegalArgumentException("Can not findMax when heap is empty.");
        return data.get(0);
    }

    //取出堆中最大的元素
    public E extractMax() {
        E ret = findMax();
        data.swap(0, data.getSize() - 1);
        data.removeLast();
        siftDown(0);
        return ret;
    }

    private void siftDown(int k) {
        while (leftChild(k) < data.getSize()) { //当大于时,说明当前节点k没有孩子了
            int j = leftChild(k);

            //当前k节点有右孩子,并且右孩子比左孩子还要大,那么j记录较大孩子的索引
            if (j + 1 < data.getSize() &&
                    data.get(j + 1).compareTo(data.get(j)) > 0)
                j = rightChild(k);
            //此时,data[j]是leftChild和rightChild中的最大值
            if (data.get(k).compareTo(data.get(j)) >= 0) break;  //如果此时,k节点比其下沉的节点左右两个都大,则不做处理
            data.swap(k, j); //否则交换两值
            k = j;  //再拿这个对应值去循环
        }
    }

    //取出堆中最大的元素, 并且替换成元素e
    public E replace(E e) {
        E ret = findMax();
        data.set(0, e);
        siftDown(0);
        return ret;
    }

}
