package org.wyz.juc.base;

import lombok.extern.slf4j.Slf4j;

/**
 * CThreadscheduling
 *
 * @author WangYouzheng
 * @version 1.0
 * @since 2023/3/5 1:45
 */
@Slf4j
public class C_Threadscheduling {
    public static void main(String[] args) {
        // Java中采取了 抢占式调度。也就是THread在CPU执行上下文中 是在来回切换的。 具体下次CPU去执行哪个线程是不知道的。
        /**
         * 但是可以通过参数进行影响权重
         * 特别注意  权重！ 最大的权重不一定百分百得到调度
         *
         * setPriority 只能支持1-10 否则报错，具体自己测试
         */
        new Thread(){
            @Override
            public void run() {
                log.info("我是1哈");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                log.info("1醒了");
            }
        }.start();

        Thread b = new Thread(){
            @Override
            public void run() {
                log.info("我是2哈");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                log.info("2醒了");
            }
        };
        b.setPriority(10);
        b.start();

        log.info("main线程的权重是：{}", Thread.currentThread().getPriority());
    }
}
