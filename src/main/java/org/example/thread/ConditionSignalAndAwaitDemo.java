package org.example.thread;

import io.netty.channel.epoll.Epoll;

import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionSignalAndAwaitDemo {
     static ReentrantLock lock = new ReentrantLock();
     static Condition condition = lock.newCondition();

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            lock.lock();
            try {
                System.out.println("do thread1 : signalAll");
                condition.signalAll();
            }finally {
                lock.unlock();
            }
        });


        Thread thread2 = new Thread(() -> {
            lock.lock();
            try {
                System.out.println("do thread2 : await");
                //  等待时间已过，返回flase，否则返回true
                boolean result = condition.await(10000, TimeUnit.MICROSECONDS);
                System.out.println(result);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });


        thread1.start();
        thread2.start();
    }


}


