package org.wyz.juc.base;

import lombok.extern.slf4j.Slf4j;

/**
 * F_DaemonThread
 * 守护线程。
 * <p>
 * 当没有非守护线程执行的时候，jvm进程就会结束
 * 也就是 当只有守护线程的时候，jvm就会结束。
 *
 * @author WangYouzheng
 * @version 1.0
 * @since 2023/3/5 20:56
 */
@Slf4j
public class F_DaemonThread {

    public static void main(String[] args) {
        log.info("启动normal");
        normalThread();

        log.info("启动daemon");
        daemonThread();

    }

    /**
     * 普通线程
     */
    static void normalThread() {
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                log.info("normalThread: {}", i);
            }
        }).start();
    }

    /**
     * 守护线程
     * 不是很重要的线程 应该设计成守护线程模式，避免影响到jvm的关闭。
     * 为什么我们要kill 9?
     */
    static void daemonThread() {
        Thread thread = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                /*try {
//                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    log.error(e.getMessage(), e);
                }*/
                log.info("daemonThread: {}", i);
            }
        });
        thread.setDaemon(true);
        thread.start();
    }
}
