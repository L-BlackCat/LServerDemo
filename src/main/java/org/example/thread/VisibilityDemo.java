package org.example.thread;

public class VisibilityDemo {
    /**
     * 主线程开启一个线程执行任务，任务中进行一个死循环，任务中创建一个boolean类型的变量用于控制循环的结束
     * 主线程跑到最后，设置任务中的boolean字段，将其设置为true，用于结束循环
     * <p>
     * 单处理器系统是否存在可见性问题：
     * 可见性问题是因为多线程编程出现的，与单核和双核无关，多线程并发是通过时间片的分配来实现的。
     * 当多个线程运行在一个单核处理器上，由于发生上下文切换，一个线程对寄存器变量的修改会作为该线程的上下文切换保存取起来，这就导致另外一个线程无法“看到”该线程对这个变脸的修改。
     * 因此，单核处理器实现的多线程编程也可能会出现可见性问题
     * <p>
     * 例子：多线程更新一个共享变量
     * <p>
     * <p>
     * 可见性和原子性的联系与区别?
     * 原子性描述的是一个线程对共享变量的更新，从另外一个线程的角度来看，他要么已经发生，要么尚未发生，而不是进行中的一个状态。
     * 因此原子性保证一个线程所读取到的共享变量的值要么是该变量的初始值，要么是该变量的相对新值，而不是更新过程中一个“半成品”的值，例如：32位系统中两个线程分别将long更新成0和-1
     */

    public static void main(String[] args) throws InterruptedException {
        TimeConsumingTsk timeConsumingTsk = new TimeConsumingTsk();
        Thread thread = new Thread(timeConsumingTsk);
        thread.start();

        long startMillis = System.currentTimeMillis();
        Thread.sleep(5000);

        timeConsumingTsk.cancel();
        long endMillis = System.currentTimeMillis();
        System.out.println(endMillis - startMillis);
    }

}


class TimeConsumingTsk implements Runnable {
    private boolean toCancel = false;

    @Override
    public void run() {
        while (!toCancel) {
            if (doExecute()) {
                break;
            }
        }
        if (toCancel) {
            System.out.println("Task was canceled.");
        } else {
            System.out.println("Task done.");
        }
    }

    private boolean doExecute() {
        boolean isDone = false;
        System.out.println("executing...");

//        try{
//        Thread.sleep(50);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        return isDone;
    }

    public void cancel() {
        toCancel = true;
        System.out.println(this + " canceled.");
    }
}
