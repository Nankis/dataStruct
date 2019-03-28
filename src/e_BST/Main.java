package e_BST;

import java.util.ArrayList;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        BST<Integer> bst = new BST<>();
        int[] nums = {5, 3, 6, 8, 4, 2};
        for (int num : nums)
            bst.add(num);

        ///////////////////////
        //        5         //
        //      /   \       //
        //     3     6      //
        //   /  \      \    //
        //  2    4      8   //
        //////////////////////
//        bst.preOrder();

//        bst.inOrder();

//        bst.postOrder();
//        System.out.println();
//        bst.preOrderNR();
//        System.out.println();
//        bst.levelOrder();

        BST<Integer> bst2 = new BST<>();
        Random random = new Random();
        int n = 1000;
        for (int i = 0; i < n; i++) {
            bst2.add(random.nextInt(10000));  //随机向二叉树添加0~10000的值
        }
        ArrayList<Integer> arrayList = new ArrayList<>();
        while (!bst2.isEmpty())
            arrayList.add(bst2.removeMin());
        System.out.println(arrayList);
        //简单测试
        for (int i = 1; i < arrayList.size(); i++)
            if (arrayList.get(i - 1) > arrayList.get(i))
                throw new IllegalArgumentException("Error.");

        System.out.println("removeMin test pass");


    }
}
