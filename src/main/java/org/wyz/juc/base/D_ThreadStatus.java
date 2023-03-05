package org.wyz.juc.base;

/**
 * D_ThreadStatus
 * 线程的状态
 *
 * @author WangYouzheng
 * @version 1.0
 * @since 2023/3/5 2:00
 */
public class D_ThreadStatus {

    /**
     * 线程一共有六种状态
     * NEW、RUNNABLE、BLOCKED、WAITING、TIMED_WAITING、TERMINATED
     * https://baijiahao.baidu.com/s?id=1658121385190352035&wfr=spider&for=pc
     */

    /**
     * "新建"（new）:
     * START
     *
     * "就绪"：有执行资格，没有CPU执行权，在等待CPU调度
     *
     * 抢到执行
     *
     * “运行”(runnable): 有CPU执行权，
     *
     * “死亡”： 线程代码执行结束
     *
     *
     * "阻塞": block  等待状态
     *
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {
        // new
        Thread thread = new Thread();

        thread.start();
        // timewaiting
        Thread.sleep(1111);
    }
}
