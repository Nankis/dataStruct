package c_Queue;

import java.util.Random;

public class Main {
    private static double testQueue(Queue q, int opCount) {
        long startTime = System.nanoTime();
        Random random = new Random();
        for (int i = 0; i < opCount; i++)
            q.enqueue(random.nextInt(Integer.MAX_VALUE));
        for (int i = 0; i < opCount; i++)
            q.dequeue();
        long endTime = System.nanoTime();
        return (endTime - startTime) / 1000000000.0;
    }

    public static void main(String[] args) {
        int opCount = 100000;
        ArrayQueue<Integer> arrayQueue = new ArrayQueue<>();
        double time1 = testQueue(arrayQueue, opCount);
        System.out.println("ArrayQueue,time:" + time1 + "s");


        //LoopQueue在这三者之中性能最好
        LoopQueue<Integer> LoopQueue = new LoopQueue<>();
        double time2 = testQueue(LoopQueue, opCount);
        System.out.println("LoopQueue,time:" + time2 + "s");

        LinkedListQueue<Integer> LinkedListQueu = new LinkedListQueue<>();
        double time3 = testQueue(LinkedListQueu, opCount);
        System.out.println("LinkedListQueue,time:" + time3 + "s");

    }
}
