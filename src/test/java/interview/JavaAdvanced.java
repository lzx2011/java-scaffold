package interview;

/**
 * ClassName: JavaAdvanced <br/>
 * Function: <br/>
 *
 * @author gary.liu
 * @date 2019/9/29
 */
public class JavaAdvanced {
/** pdd
 1.网络编程nio和netty相关，netty的线程模型，零拷贝实现
    nio看文章，







 */


/** Netease
 1.如何处理内存泄漏
    内存泄露 memory leak，是指程序在申请内存后，无法释放已申请的内存空间，一次内存泄露危害可以忽略，但内存泄露堆积后果很严重，无论多少内存,迟早会被占光。memory leak会最终会导致out of memory！
    1.先重启机器
    2.排查泄漏原因
 */

/**
 1.存在内存泄漏的可能原因：
    1.静态集合类像HashMap、Vector等的使用最容易出现内存泄露，这些静态变量的生命周期和应用程序一致，所有的对象Object也不能被释放，因为他们也将一直被Vector等应用着。
    2.各种连接，数据库连接，网络连接，IO连接等没有显示调用close关闭，不被GC回收导致内存泄露。
    3.监听器的使用，在释放对象的同时没有相应删除监听器的时候也可能导致内存泄露。
 */

}
