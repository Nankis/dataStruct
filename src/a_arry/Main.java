package a_arry;

public class Main {
    public static void main(String[] args) {
        Array<Integer> arr = new Array<>(20);
        for (int i = 0; i < 10; i++) {
            arr.addList(i);
        }
//        System.out.println(arr);
//
//        arr.add(1, 100);
//        System.out.println(arr);
//
//        arr.addFirst(200);
//        System.out.println(arr);
//
//        System.out.println(arr.get(5));
//        arr.set(1, -1);
//        System.out.println(arr);

        arr.add(1, 201);
        arr.addFirst(-2);
        System.out.println(arr);
        arr.remove(0);
        arr.remove(0);
        arr.remove(0);
        System.out.println(arr);
        for (int i = 0; i < 5; i++) {
            arr.remove(0);
        }
        System.out.println(arr);
    }
}
