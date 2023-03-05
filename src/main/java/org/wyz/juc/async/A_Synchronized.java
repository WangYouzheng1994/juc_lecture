package org.wyz.juc.async;

import lombok.extern.slf4j.Slf4j;

/**
 * A_Synchronised
 *
 * @author WangYouzheng
 * @version 1.0
 * @since 2023/3/5 23:16
 */
@Slf4j
public class A_Synchronized {
    static Integer a = 0;

    /**
     * 为什么是这个变量，后面会细讲
     */
    static Object lock = new Object();
    public static void main(String[] args) {
//        syncNormal(); // 501

//        errorDemo();
//        asyncSucce();
        addAsync3Demo();
    }

    /**
     * 流水账式做法
     */
    static void syncNormal() {
        for (int i = 0; i <= 500; i++) {
            add();
        }
        log.info("{}", a);
    }

    /**
     * 把一个线程从1 + 到500 的结果 拆成两个线程去实现这个任务，演示同步问题
     * 代码执行循环次数越多越容易展示这个问题。
     */
    static void errorDemo() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i <= 500; i++) {
                    add();

                }
                log.info("{}", a);
            }
        };
        Thread thread1 = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i <= 500; i++) {
                    add();
                }
                log.info("{}", a);
            }
        };
        thread.start();
        thread1.start();

    }


    /**
     * 一定有一个线程能打出来最终的正确结果：这个表示了 他们累加的最终结果变量a是正确的
     */
    static void asyncSucce() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i <= 500; i++) {
//                    addAsync();可行
                    addAsync2();// 可行
//                    addAsync3();不可行
                }
                log.info("{}", a);
            }
        };
        Thread thread1 = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i <= 500; i++) {
//                    addAsync();
                    addAsync2();
//                    addAsync3();不可行
                }
                log.info("{}", a);
            }
        };
        thread.start();
        thread1.start();
    }

    /**
     * 全局变量 a 自加
     */
    static void add() {
        a = a + 1;
    }

    /**
     * 同步代码方法
     */
    synchronized static void addAsync() {
        a = a + 1;
    }

    /**
     * 同步代码块
     */
    static void addAsync2() {
        synchronized (lock) {
            a = a + 1;
        }
       /* // 这个也可以。 class是唯一的。
        synchronized (A_Synchronized.class) {
            a = a+ 1;
        }*/
    }

    /**
     * 也是同步代码块 但是涉及到用哪个对象锁
     *
     * 这里没有使用Object 而是用了变量a。
     * 我们发现：锁失效了。
     * 先给答案，这个锁不是完全失效了
     * 1. 在IntegerCache内的，即-128 and 127中的时候 锁是正常的！
     * 2. 随后每次++以后产生的integer都是一个全新的变量。 如果学习过python，可以把这些基本类型看成不可变类型~。 也就是每次对他们进行修改其实都是新对象，但是java给提供了缓存机制提升性能。
     * 3. String也是如此，可以自己去看String.intern()
     * 4. synchronized 称为互斥锁。  但是这里不严谨，后面会详细讲 jvm对其进行了大量的优化。
     */
    static void addAsync3() {
        synchronized (a) {
            a = a + 1;
        }
    }

    /**
     * 当指定的锁 锁a的时候，在Integer 缓存内的大小的时候 锁还是有效的
     * java并发编程的艺术里面明确说了，不要用Integer 作为Lock
     * 说这个例子的唯一目的就是：如果要用同步代码  直接定义一个Object即可。
     */
    static void addAsync3Demo() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i <= 50; i++) {
                    addAsync3();// 可行
                }
                log.info("{}", a);
            }
        };
        Thread thread1 = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i <= 50; i++) {
//                    addAsync();
//                    addAsync2();
                    addAsync3(); // 可行
                }
                log.info("{}", a);
            }
        };
        thread.start();
        thread1.start();
    }
}
