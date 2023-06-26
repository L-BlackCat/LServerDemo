package org.example.thread;

public class LongTask implements Runnable{
    static long value = 0;
    @Override
    public void run() {
        for (int i = 0; i < 100000; i++) {
            value++;
            System.out.println(value);
        }
    }


    public static void main(String[] args) {
        Thread thread1 = new Thread(new LongTask());
        Thread thread2 = new Thread(new LongTask());


        thread1.start();
        thread2.start();
    }
}
