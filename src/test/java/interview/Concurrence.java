package interview;

/**
 * ClassName: Concurrence <br/>
 * Function: <br/>
 *
 * @author gary.liu
 * @date 2019/9/29
 */
public class Concurrence {
/** pdd
 1.ThreadLocal？应用场景？
    https://blog.csdn.net/revitalizing/article/details/63273642
 2.有多少种方法可以让线程阻塞，能说多少说多少
    wait,CountDownLatch、CyclicBarrier 、join
    https://blog.csdn.net/hao65103940/article/details/81107138
 3.讲一下多线程把，用到哪些写一下
    ThreadLocal,volatile,线程池，Callable、Future和FutureTask
 4.她问我线程池由哪些组件组成，有哪些线程池，分别怎么使用，以及拒绝策略有哪些。
    按照问题再补充下blog
    https://blog.csdn.net/revitalizing/article/details/61671858
 6.项目用到了多线程，如果线程数很多会怎么样，他问切换线程会发生什么，应该就是CPU切换上下文，具体就是寄存器和内存地址的刷新。
    导致线程池满，rt、load，cpu会上去，影响服务的健康


*/

/** kuaishou
 1.有一个场景，多线程并发，为每个线程安排一个随机的睡眠时间，设想一种数据结构去唤醒睡眠时间结束的线程，应该用哪种结构，答应该用优先级队列，也就是小顶堆，顶部是剩余睡眠时间最短的那个线程。
 2. 什么时候一个int,类型的操作是不安全的，自加呢，赋值呢,如果使用volatile修饰的话有什么作用。

 */

/** Netease
 1.如何自己实现线程池。线程池内的队列如何管理。线程池大小N的话，连续push进来M个的任务（M>>N），如何处理，比如20大小的线程池扔进来10000个任务
    看任务的重要性去判断是否丢弃，如果丢弃要如何补偿；
 */
}
