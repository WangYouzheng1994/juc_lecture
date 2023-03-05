package org.wyz.juc.base;

import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * Thread中常见的方法
 *
 * @author WangYouzheng
 * @version 1.0
 * @since 2023/3/5 1:21
 */
@Slf4j
public class B_ThreadMethod {
    public static void main(String[] args) {
        Stopwatch started = Stopwatch.createStarted();

        /**
         * Thread.currentThread 获取当前的线程
         * id 是自增的序列
         */
        log.info("{}", Thread.currentThread().getId());
        log.info("耗时：{}", started.elapsed(TimeUnit.SECONDS));

        /**
         * name
         * 1. jvm启动的时候 main线程会执行 main方法
         * 2. 默认的name名字是 Thread + id
         */
        log.info(Thread.currentThread().getName());
        log.info("耗时：{}", started.elapsed(TimeUnit.SECONDS));

        // 睡眠sleep 简单的理解到点就会继续运行，其实不是的 和一系列的东西有关 后面会说。
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
        log.info("最终耗时：{}秒", started.stop().elapsed(TimeUnit.SECONDS));

    }
}
