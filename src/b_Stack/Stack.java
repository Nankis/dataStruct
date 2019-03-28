package b_Stack;

public interface Stack<E> {
    int getSize();

    boolean isEmpty();

    void push(E e);

    E pop();  //删除一个元素

    E peek(); //查看栈顶元素
}
