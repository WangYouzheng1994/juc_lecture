package org.wyz.juc.lecture;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * 线程的创建与启动
 *
 * 三种方式的选择- THread方式： java是单继承的，可扩展性差但是简单。
 * 剩余的两种 取决于是否需要返回值。 但是这两种扩展性好，因为是implements，因此可以进行extends
 *
 * @author WangYouzheng
 * @version 1.0
 * @since 2023/3/5 0:45
 */
@Slf4j
public class A_NewThread {
    public static void main(String[] args) {
//        createThread();

//        createRunable();

        createCallable();
    }


    /**
     * 继承thread
     */
    static void createThread() {
        new MyThread().start();
//        new MyThread().run(); 主线程直接去执行了MyThread的run方法，没有启动线程。

    }

    /**
     * // 基于Thread 启动runable实现
     */
    static void createRunable() {
        // 基于Thread 启动runable实现
//        new Thread(new MyRun()).start();

        // 复合案例:当THtread 有自己的run实现，那么就不会调用Runnable
//        new MyThread(new MyRun()).start();

        // 复合案例:当THtread 有自己的run实现，那么就不会调用Runnable
//        new MyThread(new MyRun2()).start();

        // 复合案例 当Thread 没有run实现，那么就会调用RUnnable，并且展示THread.相关方法
        new Thread(new MyRun2()).start();
    }

    /**
     * 这种方式被大量应用在异步与回调。这个模型很重要~
     * callable 与 Future
     */
    static void createCallable() {
        // 1. 创建callable
        MyCallable myCallable = new MyCallable();
        // 2. 创建FutureTask
        FutureTask<List> futureTask = new FutureTask<>(myCallable);

        // 3. Thread进行Future任务启动
        Thread thread = new Thread(futureTask);
        // 4. 启动
        thread.start();


        // 5. 获取结果 这个方法是阻塞的，直到线程有返回值，这个是有很大的实际意义的。
        try {
            log.info(futureTask.get().toString());
            ;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 自己搞~ （idea都提示了~）
     */
    static void lambdaType() {

    }

    /**
     * 匿名内部类三种方式的写法
     */
    static void anonymousInnerClass() {
        new Thread(){
            @Override
            public void run() {
                log.info("我是匿名内部类Thread");
            }
        }.start();

        FutureTask thread = new FutureTask<>(new Callable<>() {
            @Override
            public Object call() throws Exception {
                return 123;
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {

            }
        }).start();
    }
}

@Slf4j
class MyThread extends Thread {

    /**
     * Allocates a new {@code Thread} object. This constructor has the same
     * effect as {@linkplain #Thread(ThreadGroup, Runnable, String) Thread}
     * {@code (null, null, gname)}, where {@code gname} is a newly generated
     * name. Automatically generated names are of the form
     * {@code "Thread-"+}<i>n</i>, where <i>n</i> is an integer.
     */
    public MyThread() {
    }

    /**
     * Allocates a new {@code Thread} object. This constructor has the same
     * effect as {@linkplain #Thread(ThreadGroup, Runnable, String) Thread}
     * {@code (null, target, gname)}, where {@code gname} is a newly generated
     * name. Automatically generated names are of the form
     * {@code "Thread-"+}<i>n</i>, where <i>n</i> is an integer.
     *
     * @param target the object whose {@code run} method is invoked when this thread
     *               is started. If {@code null}, this classes {@code run} method does
     *               nothing.
     */
    public MyThread(Runnable target) {
        super(target);
    }

    @Override
    public void run() {
        log.info("我是mythread");
    }
}

/**
 * 第二种方式，实现runnable
 */
@Slf4j
class MyRun implements Runnable {

    /**
     * When an object implementing interface {@code Runnable} is used
     * to create a thread, starting the thread causes the object's
     * {@code run} method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method {@code run} is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {

        log.info("我是myRun");
    }
}

@Slf4j
class MyRun2 implements Runnable {

    /**
     * When an object implementing interface {@code Runnable} is used
     * to create a thread, starting the thread causes the object's
     * {@code run} method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method {@code run} is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        log.info("当前线程id：{}，当前线程名字：{}", Thread.currentThread().getId(), Thread.currentThread().getName());
    }
}

/**
 * 让线程有返回值了
 */
@Slf4j
class MyCallable implements Callable<List> {

    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public List call() throws Exception {
        log.info("我是MyCallable");
        return Arrays.asList("1", "2");
    }
}
