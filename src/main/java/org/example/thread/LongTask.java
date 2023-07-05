package org.example.thread;

import java.util.Random;

public class LongTask implements Runnable{
    static long value = 0;
    @Override
    public void run() {
        for (int i = 0; i < 200; i++) {
            value++;

            System.out.println(value);
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public static void main(String[] args) {
        Thread thread1 = new Thread(new LongTask());
        Thread thread2 = new Thread(new LongTask());


        thread1.start();
        thread2.start();
    }
}
