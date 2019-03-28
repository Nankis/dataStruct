package a_arry;


public class Array<E> {
    private E[] data;
    private int size;

    public Array(int capacity) {
        data = (E[]) new Object[capacity];
        size = 0;
    }

    public Array() {
        this(10);
    }

    public Array(E[] arr) {
        data = (E[]) new Object[arr.length];
        for (int i = 0; i < arr.length; i++)
            data[i] = arr[i];
        size = arr.length;
    }

    //获取数组中元素个数
    public int getSize() {
        return size;
    }

    //获取数组容量
    public int getCapacity() {
        return data.length;
    }

    //返回数组是否为空
    public boolean isEmpty() {
        return size == 0;
    }

    //向数组中添加一个元素
    public void addList(E e) {
        add(size, e);
    }

    //查找是否有元素e
    public boolean contains(E e) {
        for (int i = 0; i < size; i++) {
            if (data[i].equals(e))
                return true;
        }
        return false;
    }

    //查找数组中元素e所在的索引,如果不存在元素e,则返回-1
    public int find(E e) {
        for (int i = 0; i < size; i++) {
            if (data[i].equals(e)) {
                return i;
            }
        }
        return -1;
    }

    //移除指定index的元素,并且返回移除的元素
    public E remove(int index) {
        if (index < 0 || index >= size)
            throw new IllegalArgumentException("Remove failed. Index is illegal");
        E ret = data[index];
        for (int i = index + 1; i < size; i++)
            data[i - 1] = data[i];
        size--;
        data[size] = null; // loitering objects !=memory leak
        if (size == data.length / 4 && data.length / 2 != 0)
            resize(data.length / 2);
        return ret;
    }

    public E removeFirst() {
        return remove(0);
    }

    public E removeLast() {
        return remove(size - 1);
    }

    //查找是否存在元素,存在则删除 只删除第一次查找到的元素
    public void removeElement(E e) {
        int index = find(e);
        if (index != -1)
            remove(index);
    }


    //在数组的第一个位置添加元素
    public void addFirst(E e) {
        add(0, e);
    }

    public void addLast(E e) {
        add(size, e);
    }

    public E getLast() {
        return get(size - 1);
    }

    public E getFirst() {
        return get(0);
    }

    //向数组指定索引添加元素
    public void add(int index, E e) {

        if (index < 0 | index > data.length)
            throw new IllegalArgumentException("Add failed. Required index>=0 and index<=size.");

        if (size == data.length) {
//            throw new IllegalArgumentException("Add failed. Array is full.");
            resize(2 * data.length);
        }

        //有可能出现data[0]=data[-1]的情况,即size=0的情况
        //而虽然data[-1]是数组下标越界了,但有时-1这个下标是有意义的,在int[]里,它被赋初值为0
        for (int i = size - 1; i >= index; i--) {
            data[i + 1] = data[i];
        }
        data[index] = e;
        size++;
    }

    //获取指定索引的元素
    public E get(int index) {
        if (index < 0 || index >= size)
            throw new IllegalArgumentException("Get failed. Index is illegal.");
        return data[index];
    }

    //    设置指定索引的值
    public void set(int index, E e) {
        if (index < 0 || index >= size)
            throw new IllegalArgumentException("Set failed. Index is illegal.");
        data[index] = e;
    }


    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append(String.format("Array:size = %d, capacity = %d", size, data.length));
        res.append('[');
        for (int i = 0; i < size; i++) {
            res.append(data[i]);
            if (i != size - 1)
                res.append(", ");
        }
        res.append(']');
        return res.toString();
    }


    private void resize(int newCapacity) {
        E[] newData = (E[]) new Object[newCapacity];
        for (int i = 0; i < size; i++)
            newData[i] = data[i];
        data = newData;
    }

    //交换两个索引对应的值
    public void swap(int i, int j) {
        if (i < 0 || i >= size || j < 0 || j >= size) throw new IllegalArgumentException("Index is Illegal.");

        E temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }

}
