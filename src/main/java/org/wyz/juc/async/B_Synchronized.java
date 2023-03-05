package org.wyz.juc.async;

import lombok.extern.slf4j.Slf4j;

/**
 * B_Synchronized
 *
 * 同步方法的锁是谁？
 * 静态方法的锁是class
 * 非静态方法的锁是this，因此在静态方法上进行同步，切记注意要保证是一个对象。 平时写Spring的时候 因为都是单例所以默认是可以的，如果发现哪儿不得劲了：赶紧先看看这个实例是不是多实例模式的。
 *
 * @author WangYouzheng
 * @version 1.0
 * @since 2023/3/5 23:58
 */
@Slf4j
public class B_Synchronized {
    public static void main(String[] args) {
        errordemo();
//        successdemo();
    }

    /**
     * 异常的演示
     */
    static void errordemo() {
        MyThread myThread = new MyThread();
        MyThread myThread1 = new MyThread();
        MyThread myThread2 = new MyThread();

        myThread1.start();
        myThread.start();
        myThread2.start();
    }

    static void successdemo() {
        MyRunnable myRunnable = new MyRunnable();
        Thread thread = new Thread(myRunnable);
        Thread thread1 = new Thread(myRunnable);
        thread.start();
        thread1.start();
    }
}

@Slf4j
class MyThread extends Thread {
    static Integer i = 0;
    @Override
    public void run() {
        for (int ii = 2000; ii < 2500; ii++) {
            /*try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }*/
            add();
        }
        log.info("{}, this:{}", i, this);
    }

    /**
     * 演示非静态方法的同步代码块失效问题
     */
    synchronized void add() {
        i = i + 1;
    }
}

@Slf4j
class MyRunnable implements Runnable {
    static Integer i = 0;
    @Override
    public void run() {
        for (int ii = 2000; ii < 2500; ii++) {
            /*try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }*/
            add();
        }
        log.info("{}, this:{}", i, this);

    }
    synchronized void add() {
        i = i + 1;
    }
}
